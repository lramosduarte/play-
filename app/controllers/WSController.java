package controllers;

import models.Tarefas;
import org.w3c.dom.Document;
import play.db.jpa.Transactional;
import play.libs.XPath;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.TheWSwsdl;
import views.html.helloResponse;
import views.html.indexsoap;
import views.html.xml.*;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

/**
 * Created by sisqualis on 14/06/16.
 */
@Transactional
public class WSController extends Controller {

    public Result index() {
        return ok(indexsoap.render("Your new application is ready."));
    }

    public Result wsdl(String wsdlParam) {
        return ok(TheWSwsdl.render()).as("text/xml");
    }

    public Result router() {
        try {
            Document dom = request().body().asXml();
            return ok(helloResponse.render("sault")).as("text/xml");
        } catch (Exception e) {
            return internalServerError(e.toString());
        }
    }

    public Result tarefasWSDL(String wsdlParam) {
        String host = request().host();
        return ok(tarefasWSDL.render(host)).as("text/xml");
    }

    @BodyParser.Of(BodyParser.Xml.class)
    public Result listaTarefas(){
        try{
            return ok(Tarefas.listarXML());
        }catch (JAXBException ex){
            return internalServerError(ex.toString());
        } catch (SOAPException e) {
            e.printStackTrace();
            return internalServerError(e.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
            return internalServerError(e.toString());
        }
    }

    @BodyParser.Of(BodyParser.Xml.class)
    public Result tarefa(){
        try{
            Document documento = request().body().asXml();
            Long codigo = Long.parseLong(XPath.selectText("//codigo", documento));
            Tarefas tarefa = Tarefas.tarefa(codigo);
            return ok(tarefa.toXML());
        }catch (NullPointerException ex){
            return internalServerError("Erro ao gerar retornar a tarefa,"
                    + " o codigo pode estar incorreto");
        }catch (Exception ex){
            return internalServerError(ex.toString());
        }
    }

    public Result inserirTarefa(){
        Document documento = request().body().asXml();
        String descricao = XPath.selectText("//descricao", documento);
        Tarefas.adicionar(Tarefas.toTarefa(descricao));
        return ok();
    }
}
