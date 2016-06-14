import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import models.Tarefas;
import org.junit.*;

import play.test.*;
import play.twirl.api.Content;

import static org.junit.Assert.*;

/**
 * Created by sisqualis on 14/06/16.
 */
public class TarefasTest {

    @Test
    public void toTarefaTest(){
        Tarefas tarefa = Tarefas.toTarefa("teste");
        assertTrue(tarefa instanceof Tarefas);
        assertNotNull(tarefa);
        assertEquals("teste", tarefa.getDescricao());
    }

    @Test
    public void setPrazoTest() {
        DateFormat formatar = new SimpleDateFormat("yyyy-MM-yy");
        Date data = new Date();
        Tarefas tarefa = new Tarefas();
        tarefa.setPrazo("2016-06-14");

        assertEquals( formatar.format(tarefa.getPrazo()), formatar.format(data));
    }
}
