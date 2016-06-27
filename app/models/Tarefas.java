package models;

import com.fasterxml.jackson.databind.JsonNode;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import play.Logger;
import play.data.DynamicForm;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

import java.lang.reflect.Field;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="TAREFAS")
@XmlRootElement(name = "Tarefa")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"id", "descricao", "prazo", "detalhes"})
public class Tarefas implements Serializable {

    @Id
    @GeneratedValue
    @XmlAttribute
    private Long id;

    @Required
    @XmlElement
    private String descricao;

    @XmlElement
    private Date prazo;

    @OneToMany(mappedBy = "tarefa")
    @XmlElement
    public List<Detalhes> detalhes;


    public Tarefas(){
        super();
    }


    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

        public String getDescricao(){
        return descricao;
    }


    public Long getId(){
        return id;
    }


    public void setId(Long id){
        this.id = id;
    }


    public void setPrazo(Date prazo){
        Logger.info("atualizar a data");
        this.prazo = prazo;
    }


    public void setPrazo(String prazo){
        SimpleDateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd");
        formatarData.setLenient(false);
        try {
            this.prazo = formatarData.parse(prazo);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }


    public Date getPrazo(){
        return prazo;
    }


    public void setTarefa(Tarefas tarefa){
        this.descricao = tarefa.getDescricao();
        this.prazo = tarefa.getPrazo();
    }

    public static void adicionar(Tarefas tarefa){
        Logger.info("adicinar uma tarefa");
        JPA.em().persist(tarefa);
    }


    public static Tarefas update(Long id, Tarefas novosDados){
        Tarefas tarefa = JPA.em().find(Tarefas.class, id);
        if(tarefa != null){
            tarefa.setTarefa(novosDados);
            return novosDados;
        }
        return tarefa;
    }


    public static Tarefas tarefa(Long id){
        Tarefas tarefa = JPA.em().find(Tarefas.class, id);
        return tarefa;
    }


    public static void remover(Long id){
        Tarefas tarefa = JPA.em().find(Tarefas.class, id);
        JPA.em().remove(tarefa);
        Logger.info("tarefa deletada", tarefa);
    }


    public String toXML() throws JAXBException, ParserConfigurationException, TransformerException, SAXException, IOException {
        JAXBContext context = JAXBContext.newInstance(Tarefas.class);
        StringWriter sw = new StringWriter();
        Marshaller agrupamento = context.createMarshaller();
        agrupamento.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        agrupamento.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        agrupamento.marshal(this, sw);
        return sw.toString();
    }

    public String newXml(StringWriter xml) throws ParserConfigurationException, SAXException, IOException, TransformerException, JAXBException {
        Document documento = parseDocument(xml);
        Tarefas classeTarefa = new Tarefas();

        Field[] campos = classeTarefa.getClass().getDeclaredFields();
        for(Field campo : campos){
            adicionarAtributo(documento, campo.getName(), "Tipo",
                    campo.getType().getSimpleName().toString());
        }
        return modificarXml(documento);
    }



    public static List<Tarefas> listar(){
        Logger.info("listando tarefas");
        Query query = JPA.em().createQuery("select e from Tarefas e order by id");
        List<Tarefas> tarefas = query.getResultList();
        return tarefas;
    }


    public static String listarXML() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(ListaTarefas.class);
        StringWriter sw = new StringWriter();
        Marshaller agrupamento = context.createMarshaller();
        agrupamento.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        agrupamento.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        agrupamento.marshal(new ListaTarefas(Tarefas.listar()), sw);
        return sw.toString();
    }


    public static Tarefas toTarefa(String descricao){
        Tarefas tarefa = new Tarefas();
        tarefa.descricao = descricao;
        return tarefa;
    }


    public static Tarefas toTarefa(Long id, DynamicForm form){
        Tarefas tarefa = new Tarefas();
        tarefa.setId(id);
        tarefa.setPrazo(form.get("tarefaPrazo"));
        tarefa.setDescricao(form.get("tarefaDescricao"));
        return tarefa;
    }

    public static Tarefas toTarefa(JsonNode json){
        Tarefas tarefa = Tarefas.toTarefa(json.findPath("descricao").textValue());
        tarefa.setPrazo(json.findPath("prazo").textValue());
        return tarefa;
    }

    private Document parseDocument(StringWriter xml) throws IOException,
            SAXException, ParserConfigurationException {
        InputSource stream = new InputSource(new StringReader(xml.toString()));
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(stream);
    }

    private String modificarXml(Document documento) throws TransformerException, JAXBException{
        DOMImplementationLS documentImplementacao = (DOMImplementationLS) documento
                .getImplementation();
        LSSerializer serializer = documentImplementacao.createLSSerializer();

        return serializer.writeToString(documento);

    }

    private void adicionarAtributo(Document documento, String tag, String tipo,
                                   String tipoValor){
        NodeList tarefas = documento.getElementsByTagName(tag);
        for(int i = 0; i < tarefas.getLength(); i++){
            ((Element)tarefas.item(i)).setAttribute(tipo, tipoValor);
        }
    }
}
