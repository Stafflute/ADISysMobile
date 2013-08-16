package integration.xml.parser;

import business.entity.InterventoCompleto;
import org.xml.sax.SAXException;
import pl.polidea.TreeXMLParser.XMLLeafNode;
import pl.polidea.TreeXMLParser.XMLNode;
import pl.polidea.TreeXMLParser.XMLParser;
import util.ErrorPrinter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JournalingSimpleParser {

    public static final int FIRST = 0;

    public static List<InterventoCompleto> parse(File file) {
        List<InterventoCompleto> pianificazione = null;
        XMLParser parser = new XMLParser();
        try {
            XMLNode root = parser.parse(new FileInputStream(file));
            pianificazione = deserialize(root);
        } catch (SAXException e) {
            ErrorPrinter.print(e);
        } catch (ParserConfigurationException e) {
            ErrorPrinter.print(e);
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }
        return pianificazione;
    }

    private static List<InterventoCompleto> deserialize(XMLNode root) {
        List<InterventoCompleto> listaInterventi = new ArrayList<>();

        List<XMLNode> nodeList = root.children.get(FIRST).queryNodes("intervento");
        for (XMLNode node : nodeList) {
            InterventoCompleto intervento = new InterventoCompleto();

            String id = getLeafNodeValue(node, "id");
            intervento.setId(id);

            listaInterventi.add(intervento);
        }

        return listaInterventi;
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
