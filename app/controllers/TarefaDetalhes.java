package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by sisqualis on 15/06/16.
 */
@Transactional
public class TarefaDetalhes extends Controller{

    public Result detalhes(Long id){
        return ok();
    }

}
