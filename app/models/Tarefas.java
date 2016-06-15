package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.DynamicForm;
import play.data.validation.Constraints.*;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.*;


@Entity
@Table(name="TAREFAS")
public class Tarefas implements java.io.Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Required
    private String descricao;

    private Date prazo;

    @OneToMany(mappedBy = "tarefa")
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
        return JPA.em().find(Tarefas.class, id);
    }


    public static void remover(Long id){
        Tarefas tarefa = JPA.em().find(Tarefas.class, id);
        JPA.em().remove(tarefa);
        Logger.info("tarefa deletada", tarefa);
    }


    public static List<Tarefas> listar(){
        Logger.info("listando tarefas");
        Query query = JPA.em().createQuery("select e from Tarefas e order by id");
        List<Tarefas> tarefas = query.getResultList();
        return tarefas;
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

}
