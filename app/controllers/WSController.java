package controllers;

import models.Tarefas;
import org.w3c.dom.Document;
import play.db.jpa.Transactional;
import play.libs.XPath;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.TheWSwsdl;
import views.html.helloResponse;
import views.html.indexsoap;
import views.html.xml.*;

import javax.xml.bind.JAXBException;

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
        return ok(tarefasWSDL.render()).as("text/xml");
    }

    public Result listaTarefas(){
        try{
            return ok(tarefasResponse.render(Tarefas.listarXML())).as("text/xml");
        }catch (JAXBException ex){
            return internalServerError(ex.toString());
        }
    }

    public Result tarefa(){
        try{
            Document documento = request().body().asXml();
            Long codigo = Long.parseLong(XPath.selectText("//codigo", documento));
            Tarefas tarefa = Tarefas.tarefa(codigo);
            return ok(tarefaResponse.render(tarefa.toXML())).as("text/xml");
        }catch (NullPointerException ex){
            return internalServerError(tarefaResponse.render(
                    "Erro ao gerar retornar a tarefa,"
                    + " o codigo pode estar incorreto")).as("text/xml");
        }catch (Exception ex){
            return internalServerError(tarefaResponse.render(ex.toString()));
        }
    }

    public Result inserirTarefa(){
        Document documento = request().body().asXml();
        String descricao = XPath.selectText("//descricao", documento);
        Tarefas.adicionar(Tarefas.toTarefa(descricao));
        return ok();
    }

    public Result tarefasXSD(){
        return ok(tarefasXSD.render()).as("text/xml");
    }
}
