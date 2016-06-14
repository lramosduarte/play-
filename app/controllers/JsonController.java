package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import models.Tarefas;

import java.util.List;

/**
 * Created by sisqualis on 14/06/16.
 */
@Transactional
public class JsonController extends Controller {

    public Result tarefas(){
        Logger.info("Listando as tarefas em json");
        List<Tarefas> tarefas = Tarefas.listar();
        return ok(Json.toJson(tarefas));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result update(){
        JsonNode json = request().body().asJson();
        Tarefas tarefa = Tarefas.toTarefa(json);
        Long id = Long.parseLong(json.findPath("id").textValue());
        return ok(Json.toJson(Tarefas.update(id, tarefa)));
    }

}
