package models;

import java.util.*;

import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Tarefas{

    private static Long idAutoIncrement = 0L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Required
    public String descricao;

    public Tarefas(){
        idAutoIncrement++;
        setId(idAutoIncrement);
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


    public static Tarefas toTarefa(String descricao){
        Tarefas tarefa = new Tarefas();
        tarefa.descricao = descricao;
        return tarefa;
    }
}
