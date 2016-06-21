package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by sisqualis on 15/06/16.
 */
@Entity
@Table(name="DETALHES")
public class Detalhes implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String comentario;

    @ManyToOne
    private Tarefas tarefa;

    public Detalhes(){
        super();
    }


    public void setId(Long id){
        this.id = id;
    }


    public Long getId(){
        return id;
    }


    public void setComentario(String comentario){
        this.comentario = comentario;
    }


    public String getComentario(){
        return comentario;
    }


    public static List<Detalhes> detalhes(Long tarefa){
        Query query= JPA.em().createQuery("select e from Detalhes e "
                        + "where tarefa_id = " + tarefa);

        return query.getResultList();
    }
}
