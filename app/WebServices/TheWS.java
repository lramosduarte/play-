package WebServices;


import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class TheWS {

    public String hello(String message) {
        return "hello dasdasdasdasdas" + message;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9000/TheWS", new TheWS());
    }
}