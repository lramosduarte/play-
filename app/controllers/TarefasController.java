package controllers;

import play.mvc.*;

import play.data.DynamicForm;
import play.data.Form;

import views.html.*;
import models.Tarefas;

import java.util.ArrayList;

public class TarefasController extends Controller {

    private static ArrayList<Tarefas> lista = new ArrayList<Tarefas>();

    public Result tarefas() {
        return ok(tarefas.render(lista));
    }

    public Result adicionar() {
        DynamicForm requestData = Form.form().bindFromRequest();
        lista.add(Tarefas.toTarefa(requestData.get("descricao")));
        return redirect(routes.TarefasController.tarefas());
    }

    public Result remover(Long id){
        return redirect(routes.TarefasController.tarefas());
    }
}
