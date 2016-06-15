package controllers;

import models.Detalhes;
import models.Tarefas;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Created by sisqualis on 15/06/16.
 */
@Transactional
public class TarefaDetalhes extends Controller{

    public Result detalhes(Long id){
        return ok(tarefasDetalhes.render(Tarefas.tarefa(id), Detalhes.detalhes(id)));
    }

}
