// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 03/09/2013 12.51.16
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLParser.java

package utility.treexmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package pl.polidea.TreeXMLParser:
//            XMLNode, XMLInternalNode, XMLLeafNode

public class XMLParser extends DefaultHandler
{

    public XMLParser()
    {
    }

    public XMLNode parse(String xml)
            throws SAXException, ParserConfigurationException, IOException
    {
        InputStream xmlStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        return parse(xmlStream);
    }

    public XMLNode parse(InputStream xmlStream)
            throws SAXException, ParserConfigurationException, IOException
    {
        rootNode = new XMLNode();
        currentNode = rootNode;
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.parse(new InputSource(xmlStream));
        return rootNode;
    }

    public void startElement(String uri, String localName, String qName, Attributes atts)
            throws SAXException
    {
        Map attributes = new HashMap();
        for(int i = 0; i < atts.getLength(); i++)
            attributes.put(atts.getLocalName(i), atts.getValue(i));

        XMLInternalNode node = new XMLInternalNode(currentNode, localName, attributes);
        currentNode.children.add(node);
        currentNode = node;
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        currentNode = currentNode.parent;
    }

    public void characters(char ch[], int start, int length)
            throws SAXException
    {
        String string = new String(ch, start, length);
        if(currentNode.children.size() != 0)
        {
            XMLNode lastChild = (XMLNode)currentNode.children.get(currentNode.children.size() - 1);
            if(lastChild instanceof XMLLeafNode)
            {
                String toElaborate = "";
                if(string.matches("(.)*\\r?\\n"))
                    toElaborate = (new StringBuilder()).append(string.trim()).append("\n").toString();
                else
                    toElaborate = string.trim();
                ((XMLLeafNode)lastChild).value.append(toElaborate);
            } else
            if(!string.matches(BLANK_CONTENT_REGEX))
                currentNode.children.add(new XMLLeafNode(currentNode, string.trim()));
        } else
        if(!string.matches(BLANK_CONTENT_REGEX))
            currentNode.children.add(new XMLLeafNode(currentNode, string.trim()));
    }

    private String trimMultiLineText(String string)
    {
        String raw = string.trim();
        String rows[] = raw.split("\\r?\\n");
        String result = "";
        result = raw;
        return result;
    }

    private XMLNode rootNode;
    private XMLNode currentNode;
    private static final String BLANK_CONTENT_REGEX = "(\\s)*";
}