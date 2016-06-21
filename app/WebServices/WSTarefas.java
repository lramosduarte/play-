package WebServices;

import models.Tarefas;

import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Endpoint;

/**
 * Created by sisqualis on 20/06/16.
 */
@WebService
public class WSTarefas {

    public String tarefas() throws JAXBException {
        return Tarefas.listarXML();
    }

    public static void main(String[] args){
        Endpoint.publish("http://localhost:9000/WSTarefas", new WSTarefas());
    }

}
