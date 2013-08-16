package integration.xml.builder;

import business.entity.*;
import org.joda.time.LocalTime;
import org.xml.sax.SAXException;
import pl.polidea.TreeXMLParser.XMLInternalNode;
import pl.polidea.TreeXMLParser.XMLLeafNode;
import pl.polidea.TreeXMLParser.XMLNode;
import pl.polidea.TreeXMLParser.XMLParser;
import util.ErrorPrinter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JournalingBuilder {

    private static final String JOURNALING_ELEMENT = "journaling";

    public static void buildEmptyJournaling(File journaling) {
        XMLNode root = new XMLNode();
        XMLInternalNode journalingNode = new XMLInternalNode(root, JOURNALING_ELEMENT, null);

        List<XMLNode> nodeList = new LinkedList<>();
        nodeList.add(journalingNode);

        root.children = nodeList;

        try {
            journaling.createNewFile();
            PrintWriter fileWriter = new PrintWriter(journaling);

            fileWriter.print(root.generateXML());

            fileWriter.close();
        } catch (FileNotFoundException e) {
            ErrorPrinter.print(e);
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }
    }

    private static final int FIRST = 0;

    public static void fillInterventoCompleto(File journaling, InterventoCompleto interventoCompleto) {
        XMLParser parser = new XMLParser();
        try {
            XMLNode root = parser.parse(new FileInputStream(journaling));
            XMLInternalNode journalingNode = (XMLInternalNode) root.children.get(FIRST);
            root.children.set(FIRST, serialize(journalingNode, interventoCompleto));

            PrintWriter fileWriter = new PrintWriter(journaling);
            String rawFormattedXML = root.generateXML();
            fileWriter.print(rawFormattedXML);
            fileWriter.close();
        } catch (SAXException e) {
            ErrorPrinter.print(e);
        } catch (ParserConfigurationException e) {
            ErrorPrinter.print(e);
        } catch (IOException e) {
            ErrorPrinter.print(e);
        }
    }

    private static XMLInternalNode createSimpleElement(XMLNode parent, String tag, String value) {
        XMLInternalNode result = new XMLInternalNode(parent, tag, null);
        List<XMLNode> leafList = new LinkedList<>();
        XMLLeafNode valueNode = new XMLLeafNode(result, value);
        leafList.add(valueNode);
        result.children = leafList;
        return result;
    }

    private static void appendElement(XMLNode parent, XMLInternalNode child) {
        List<XMLNode> children = parent.children;
        if (children == null) {
            children = new LinkedList<>();
        }

        children.add(child);
        parent.children = children;
    }

    private static void appendSimpleElement(XMLNode parent, String tag, String value) {
        XMLInternalNode internalNode = createSimpleElement(parent, tag, value);
        appendElement(parent, internalNode);
    }

    private static XMLInternalNode createSimpleElement(XMLNode parent, String tag, String value, Map<String, String> map) {
        XMLInternalNode result = new XMLInternalNode(parent, tag, map);
        List<XMLNode> leafList = new LinkedList<>();
        XMLLeafNode valueNode = new XMLLeafNode(result, value);
        leafList.add(valueNode);
        result.children = leafList;
        return result;
    }

    private static void appendSimpleElement(XMLNode parent, String tag, String value, Map<String, String> map) {
        XMLInternalNode internalNode = createSimpleElement(parent, tag, value, map);
        appendElement(parent, internalNode);
    }

    private static XMLInternalNode serialize(XMLInternalNode journalingNode, InterventoCompleto interventoCompleto) {
        XMLInternalNode interventoNode = new XMLInternalNode(journalingNode, "intervento", null);
        appendElement(journalingNode, interventoNode);

        appendSimpleElement(interventoNode, "id", interventoCompleto.getId());
        appendSimpleElement(interventoNode, "citta", interventoCompleto.getCitta());
        appendSimpleElement(interventoNode, "cap", interventoCompleto.getCap());
        appendSimpleElement(interventoNode, "indirizzo", interventoCompleto.getIndirizzo());
        appendSimpleElement(interventoNode, "data", interventoCompleto.getData().toString());
        appendSimpleElement(interventoNode, "ora", interventoCompleto.getOra().toString());

        fillListaOperazioni(interventoNode, interventoCompleto);
        fillPaziente(interventoNode, interventoCompleto);
        fillInfermiere(interventoNode, interventoCompleto);
        fillListaGPS(interventoNode, interventoCompleto);
        fillListaAccelerometro(interventoNode, interventoCompleto);

        return journalingNode;
    }

    private static void fillListaOperazioni(XMLNode interventoNode, InterventoCompleto interventoCompleto) {
        XMLInternalNode listaOperazioniNode = new XMLInternalNode(interventoNode, "listaOperazioni", null);
        appendElement(interventoNode, listaOperazioniNode);

        List<XMLNode> operazioneListNode = new LinkedList<>();
        for (Operazione operazione : interventoCompleto.getOperazione()) {
            XMLInternalNode operazioneNode = new XMLInternalNode(listaOperazioniNode, "operazione", null);

            appendSimpleElement(operazioneNode, "id", operazione.getId());
            appendSimpleElement(operazioneNode, "nome", operazione.getNome());
            appendSimpleElement(operazioneNode, "nota", operazione.getNota());

            ValoreRilevato valoreRilevato = operazione.getValoreRilevato();
            Map<String, String> valoreRilevatoAttributes = new HashMap<>();
            if (valoreRilevato != null) {
                valoreRilevatoAttributes.put("tempoOperazione", valoreRilevato.getTempoOperazione().toString());
                appendSimpleElement(operazioneNode, "valoreRilevato", valoreRilevato.getMisura(), valoreRilevatoAttributes);
            } else {
                valoreRilevatoAttributes.put("tempoOperazione", (new LocalTime(0)).toString());
                appendSimpleElement(operazioneNode, "valoreRilevato", "", valoreRilevatoAttributes);
            }

            operazioneListNode.add(operazioneNode);
        }

        listaOperazioniNode.children = operazioneListNode;
    }

    private static void fillPaziente(XMLNode interventoNode, InterventoCompleto interventoCompleto) {
        XMLInternalNode pazienteNode = new XMLInternalNode(interventoNode, "paziente", null);
        appendElement(interventoNode, pazienteNode);

        Paziente paziente = interventoCompleto.getPaziente();

        appendSimpleElement(pazienteNode, "id", paziente.getId());
        appendSimpleElement(pazienteNode, "nome", paziente.getNome());
        appendSimpleElement(pazienteNode, "cognome", paziente.getCognome());
        appendSimpleElement(pazienteNode, "data", paziente.getData().toString());

        XMLInternalNode rubricaNode = new XMLInternalNode(pazienteNode, "rubrica", null);
        appendElement(pazienteNode, rubricaNode);

        if (paziente.getNumeroCellulare() != null) {
            List<XMLNode> numeroNodeList = new LinkedList<>();
            for (String numero : paziente.getNumeroCellulare()) {
                appendSimpleElement(rubricaNode, "numero", numero);
            }
        }
    }

    private static void fillInfermiere(XMLNode interventoNode, InterventoCompleto interventoCompleto) {
        XMLInternalNode infermiereNode = new XMLInternalNode(interventoNode, "infermiere", null);
        appendElement(interventoNode, infermiereNode);

        Infermiere infermiere = interventoCompleto.getInfermiere();

        appendSimpleElement(infermiereNode, "id", infermiere.getId());
        appendSimpleElement(infermiereNode, "nome", infermiere.getNome());
        appendSimpleElement(infermiereNode, "cognome", infermiere.getCognome());
    }

    private static void fillListaGPS(XMLNode interventoNode, InterventoCompleto interventoCompleto) {
        XMLInternalNode listaGPSNode = new XMLInternalNode(interventoNode, "listaGPS", null);
        appendElement(interventoNode, listaGPSNode);

        List<XMLNode> gpsListNode = new LinkedList<>();
        for (GPS gps : interventoCompleto.getGps()) {
            XMLInternalNode gpsNode = new XMLInternalNode(listaGPSNode, "gps", null);

            appendSimpleElement(gpsNode, "latitudine", "" + gps.getLatitudine());
            appendSimpleElement(gpsNode, "longitudine", "" + gps.getLongitudine());

            gpsListNode.add(gpsNode);
        }

        listaGPSNode.children = gpsListNode;
    }

    private static void fillListaAccelerometro(XMLNode interventoNode, InterventoCompleto interventoCompleto) {
        XMLInternalNode listaAccelerometroNode = new XMLInternalNode(interventoNode, "listaAccelerometro", null);
        appendElement(interventoNode, listaAccelerometroNode);

        List<XMLNode> accelerometroListNode = new LinkedList<>();
        for (Accelerometro accelerometro : interventoCompleto.getAccelerometro()) {
            XMLInternalNode accelerometroNode = new XMLInternalNode(listaAccelerometroNode, "accelerometro", null);

            appendSimpleElement(accelerometroNode, "x", "" + accelerometro.getX());
            appendSimpleElement(accelerometroNode, "y", "" + accelerometro.getY());
            appendSimpleElement(accelerometroNode, "z", "" + accelerometro.getZ());
            appendSimpleElement(accelerometroNode, "data", accelerometro.getData().toLocalDate().toString());
            appendSimpleElement(accelerometroNode, "ora", accelerometro.getData().toLocalTime().toString());

            accelerometroListNode.add(accelerometroNode);
        }

        listaAccelerometroNode.children = accelerometroListNode;
    }
}
