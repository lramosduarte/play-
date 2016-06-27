package Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import play.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by sisqualis on 27/06/16.
 */
public class ManipularXml {

    public static void adicionarAtributo(Document documento, String tag, String tipo,
                                   String tipoValor){
        NodeList tarefas = documento.getElementsByTagName(tag);
        for(int i = 0; i < tarefas.getLength(); i++){
            ((Element)tarefas.item(i)).setAttribute(tipo, tipoValor);
            Logger.info(tarefas.item(i).getNodeName());
        }
    }

    public static Document parseDocument(String xml) throws IOException,
            SAXException, ParserConfigurationException {
        InputSource stream = new InputSource(new StringReader(xml));
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(stream);
    }

}
