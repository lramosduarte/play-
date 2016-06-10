package controllers;

import play.mvc.*;

import play.data.DynamicForm;
import play.data.Form;
import play.Logger;
import play.db.jpa.Transactional;

import views.html.*;
import models.Tarefas;

import java.util.ArrayList;

@Transactional
public class TarefasController extends Controller {


    public Result tarefas() {
        return ok(tarefas.render(Tarefas.listar()));
    }

    public Result adicionar() {
        DynamicForm requestData = Form.form().bindFromRequest();
        Tarefas tarefa = Tarefas.toTarefa(requestData.get("descricao"));
        Tarefas.adicionar(tarefa);
        return redirect(routes.TarefasController.tarefas());
    }

    public Result remover(Long id){
        return redirect(routes.TarefasController.tarefas());
    }
}
