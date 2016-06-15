package models;

import javax.persistence.*;

/**
 * Created by sisqualis on 15/06/16.
 */
@Entity
@Table(name="DETALHES")
public class Detalhes implements java.io.Serializable {
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
}
