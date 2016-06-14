import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import models.Tarefas;
import org.junit.*;

import play.test.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class ApplicationTest extends WithApplication{

    @Test
    public void TarefasViewTemplate(){
        List<Tarefas> tarefas = new ArrayList<Tarefas>();
        Content html = views.html.tarefas.render(tarefas);
        assertTrue(contentAsString(html).contains("Nenhuma"));
    }

}
