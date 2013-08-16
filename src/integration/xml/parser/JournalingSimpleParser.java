package integration.xml.parser;

import org.xml.sax.SAXException;
import pl.polidea.TreeXMLParser.XMLLeafNode;
import pl.polidea.TreeXMLParser.XMLNode;
import pl.polidea.TreeXMLParser.XMLParser;
import util.ErrorPrinter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JournalingSimpleParser {

    public static final int FIRST = 0;

    public static Set<String> parse(File file) {
        Set<String> idSet = null;
        XMLParser parser = new XMLParser();
        try {
            XMLNode root = parser.parse(new FileInputStream(file));
            idSet = deserialize(root);
        } catch (SAXException e) {
            ErrorPrinter.print(e);
        } catch (ParserConfigurationException e) {
            ErrorPrinter.print(e);
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }
        return idSet;
    }

    private static Set<String> deserialize(XMLNode root) {
        Set<String> idSet = new HashSet<>();

        List<XMLNode> nodeList = root.children.get(FIRST).queryNodes("intervento");
        for (XMLNode node : nodeList) {
            String id = getLeafNodeValue(node, "id");

            idSet.add(id);
        }

        return idSet;
    }

    private static String getLeafNodeValue(XMLNode node, String tag) {
        List<XMLNode> nodeList = node.queryNode(tag).children;
        String result = null;
        if (!nodeList.isEmpty()) {
            XMLLeafNode leafNode = (XMLLeafNode) nodeList.get(FIRST);
            result = leafNode.value.toString();
        }
        return result;
    }
}
