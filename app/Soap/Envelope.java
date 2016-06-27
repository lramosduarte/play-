package Soap;

import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by sisqualis on 27/06/16.
 */
public class Envelope {
    private MessageFactory factory;
    private SOAPMessage mensagem;
    private SOAPPart part;
    private SOAPEnvelope envelope;
    private SOAPHeader header;
    private SOAPBody body;

    public Envelope() throws SOAPException {
        factory = MessageFactory.newInstance();
        mensagem = factory.createMessage();
        part = mensagem.getSOAPPart();
        envelope = part.getEnvelope();
        header = envelope.getHeader();
        body = envelope.getBody();
    }

    public String gerarEnvelope(String xml) throws SOAPException, TransformerException {
        body.addTextNode(xml);
        return serializar();
    }
    public String gerarEnvelope(Document xml) throws SOAPException, TransformerException {
        body.addDocument(xml);
        return serializar();
    }

    private String serializar() throws TransformerException, SOAPException {
        Document documento = formatar();
        DOMImplementationLS documentImplementacao = (DOMImplementationLS) documento
                .getImplementation();
        LSSerializer serializer = documentImplementacao.createLSSerializer();
        String newXml = serializer.writeToString(documento);
        return newXml.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
    }

    private Document formatar() throws TransformerException, SOAPException {
        Source src = mensagem.getSOAPPart().getContent();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMResult result = new DOMResult();
        transformer.transform(src, result);
        return (Document)result.getNode();
    }
}
