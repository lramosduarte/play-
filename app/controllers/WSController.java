package controllers;

import models.Tarefas;
import org.w3c.dom.Document;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.XPath;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.TheWSwsdl;
import views.html.helloResponse;
import views.html.indexsoap;
import views.html.xml.tarefasWSDL;
import views.html.xml.tarefasResponse;
import views.html.xml.tarefasXSD;

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
            Logger.info(dom.toString());
            return ok(helloResponse.render("sault")).as("text/xml");
        } catch (Exception e) {
            Logger.error(e.toString(), e);
            return internalServerError(e.toString());
        }
    }

    public Result tarefasWSDL(String wsdlParam) {
        return ok(tarefasWSDL.render()).as("text/xml");
    }

    public Result listaTarefas(){
        try{
            Document documento = request().body().asXml();
            String descricao = XPath.selectText("//descricao", documento);
            Logger.info(descricao);
            return ok(tarefasResponse.render(Tarefas.listarXML()));
        }catch (Exception ex){
            Logger.error(ex.toString(), ex);
            return internalServerError(ex.toString());
        }
    }

    public Result tarefasXSD(){
        return ok(tarefasXSD.render()).as("text/xml");
    }
}
