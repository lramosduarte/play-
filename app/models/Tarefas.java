package models;

import java.util.*;

import play.data.validation.Constraints.*;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.*;


@Entity
@Table(name="TAREFAS")
public class Tarefas {

    // private static Long idAutoIncrement = 0L;


    @Id
    @GeneratedValue
    private Long id;

    @Required
    private String descricao;

    // public Tarefas(){
    //     idAutoIncrement++;
    //     setId(idAutoIncrement);
    // }


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


    public static void adicionar(Tarefas tarefa){
        Logger.info("adicinar uma tarefa");
        JPA.em().persist(tarefa);
    }


    public static void remover(Long id){
        Tarefas tarefa = JPA.em().find(Tarefas.class, id);
        JPA.em().remove(tarefa);
        Logger.info("tarefa deletada", tarefa);
    }


    public static List<Tarefas> listar(){
        Logger.info("listando tarefas");
        Query query = JPA.em().createQuery("select e from Tarefas e");
        List<Tarefas> tarefas = query.getResultList();
        return tarefas;
    }



    public static Tarefas toTarefa(String descricao){
        Tarefas tarefa = new Tarefas();
        tarefa.descricao = descricao;
        return tarefa;
    }
}
