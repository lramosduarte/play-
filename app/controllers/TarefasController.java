package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;

import play.data.DynamicForm;
import play.data.Form;
import play.Logger;
import play.db.jpa.Transactional;

import views.html.*;
import models.Tarefas;


@Transactional
public class TarefasController extends Controller {


    public Result tarefas() {
        Logger.info("Pagina inicial");
        return ok(tarefas.render(Tarefas.listar()));
    }


    public Result adicionar() {
        DynamicForm requestData = Form.form().bindFromRequest();
        Tarefas tarefa = Tarefas.toTarefa(requestData.get("descricao"));
        Tarefas.adicionar(tarefa);
        return redirect(routes.TarefasController.tarefas());
    }


    public Result remover(Long id){
        Logger.info("Teste de rota para remover tarefa");
        Tarefas.remover(id);
        return redirect(routes.TarefasController.tarefas());
    }


    public Result modificar(Long id){
        DynamicForm requestData = Form.form().bindFromRequest();
        Tarefas tarefa = Tarefas.toTarefa(id, requestData);
        Tarefas.update(id, tarefa);
        return redirect(routes.TarefasController.tarefas());
    }
}
