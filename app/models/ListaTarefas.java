package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sisqualis on 21/06/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Tarefas")
public class ListaTarefas {
    @XmlElement(name = "tarefa", type = Tarefas.class)
    private List<Tarefas> tarefas = new ArrayList<>();

    public ListaTarefas(){}

    public ListaTarefas(List<Tarefas> tarefas){
        this.tarefas = tarefas;
    }

    public List<Tarefas> getTarefas(){
        return tarefas;
    }
}
