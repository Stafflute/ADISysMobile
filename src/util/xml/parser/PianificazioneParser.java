package util.xml.parser;

import android.util.Log;
import business.entity.*;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.xml.sax.SAXException;
import pl.polidea.TreeXMLParser.XMLInternalNode;
import pl.polidea.TreeXMLParser.XMLLeafNode;
import pl.polidea.TreeXMLParser.XMLNode;
import pl.polidea.TreeXMLParser.XMLParser;
import util.ErrorPrinter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: michelesummo
 * Date: 13/08/13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
public class PianificazioneParser {
    public static final int FIRST = 0;

    public static Pianificazione parse(File file) {
        Pianificazione pianificazione = null;
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

    private static Pianificazione deserialize(XMLNode root) {
        Pianificazione pianificazione = new Pianificazione();

        List<Intervento> listaInterventi = new ArrayList<>();

        List<XMLNode> nodeList = root.children.get(FIRST).queryNodes("intervento");
        for (XMLNode node : nodeList) {
            Intervento intervento = new Intervento();

            String id = getLeafNodeValue(node, "id");
            intervento.setId(id);

            String citta = getLeafNodeValue(node, "citta");
            intervento.setCitta(citta);

            String cap = getLeafNodeValue(node, "cap");
            intervento.setCap(cap);

            String indirizzo = getLeafNodeValue(node, "indirizzo");
            intervento.setIndirizzo(indirizzo);

            LocalDate data = LocalDate.parse(getLeafNodeValue(node, "data"));
            intervento.setData(data);

            LocalTime ora = LocalTime.parse(getLeafNodeValue(node, "ora"));
            intervento.setOra(ora);

            Paziente paziente = fillPaziente(node);
            intervento.setPaziente(paziente);

            Infermiere infermiere = fillInfermiere(node);
            intervento.setInfermiere(infermiere);

            List<Operazione> operazione = getListOperazioni(node);
            intervento.setOperazione(operazione);

            listaInterventi.add(intervento);
        }

        pianificazione.setIntervento(listaInterventi);

        return pianificazione;
    }

    private static String getLeafNodeValue(XMLNode node, String tag) {
        List<XMLNode> nodeList = node.queryNode(tag).children;
        String result = null;
        if(!nodeList.isEmpty()) {
            XMLLeafNode leafNode = (XMLLeafNode) nodeList.get(FIRST);
            result = leafNode.value.toString();
        }
        return result;
    }

    private static Infermiere fillInfermiere(XMLNode interventoNode) {
        Infermiere infermeriere = new Infermiere();

        XMLNode node = interventoNode.queryNode("infermiere");

        String id = getLeafNodeValue(node, "id");
        infermeriere.setId(id);

        String nome = getLeafNodeValue(node, "nome");
        infermeriere.setNome(nome);

        String cognome = getLeafNodeValue(node, "cognome");
        infermeriere.setCognome(cognome);

        return infermeriere;
    }

    private static Paziente fillPaziente(XMLNode interventoNode) {
        Paziente paziente = new Paziente();

        XMLNode node = interventoNode.queryNode("paziente");

        String id = getLeafNodeValue(node, "id");
        paziente.setId(id);

        String nome = getLeafNodeValue(node, "nome");
        paziente.setNome(nome);

        String cognome = getLeafNodeValue(node, "cognome");
        paziente.setCognome(cognome);

        LocalDate data = LocalDate.parse(getLeafNodeValue(node, "data"));
        paziente.setData(data);

        XMLNode rubricaNode = node.queryNode("rubrica");
        List<XMLNode> numeroNodeList = rubricaNode.queryNodes("numero");
        paziente.setNumeroCellulare(getListLeafNode(numeroNodeList));

        return paziente;
    }

    private static List<String> getListLeafNode(List<XMLNode> nodeList) {
        List<String> list = new LinkedList<String>();

        for (XMLNode node : nodeList) {
            XMLLeafNode leafNode = (XMLLeafNode) node.children.get(FIRST);
            String element = leafNode.value.toString();
            list.add(element);
        }

        return list;
    }

    private static List<Operazione> getListOperazioni(XMLNode interventoNode) {
        List<Operazione> list = new LinkedList<>();
        XMLNode listaOperazioniNode = interventoNode.queryNode("listaOperazioni");
        List<XMLNode> nodeList = listaOperazioniNode.queryNodes("operazione");

        for (XMLNode node : nodeList) {
            Operazione operazione = new Operazione();

            String id = getLeafNodeValue(node, "id");
            operazione.setId(id);

            String nome = getLeafNodeValue(node, "nome");
            operazione.setNome(nome);

            String nota = getLeafNodeValue(node, "nota");
            operazione.setNota(nota);

            list.add(operazione);
        }

        return list;
    }
}
