package pl.kozyra.boardGame.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.kozyra.boardGame.domain.RPGFigure;
import pl.kozyra.boardGame.service.RPGFigureManager;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/*
This example uses MockMvc to mock server and http requests. This way we don't need to run real server.
We can also use Mockito to mock access to database.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RPGFigureControllerMockMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RPGFigureManager service;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void HelloWorldTest() throws Exception {
        this.mockMvc.perform(get("/"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }


    @Test
    public void getAllEmptyTest() throws Exception {
        when(service.findAllRPGFigure()).thenReturn(new LinkedList<RPGFigure>());
        this.mockMvc.perform(get("/RPGFigures"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getAllFullTest() throws Exception {
        List<RPGFigure> expectedResult = new LinkedList<RPGFigure>();
        RPGFigure np = new RPGFigure();
        np.setId(13L);
        np.setName("Runic Golem");
        np.setHP(300);
        expectedResult.add(np);
        when(service.findAllRPGFigure()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/RPGFigures"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":13,\"name\":\"Runic Golem\",\"hp\":300}]"));
    }
    @Test
    public void postNewRPGFigureTest() throws Exception {
        RPGFigure p = new RPGFigure();
        p.setName("Runic Golem");
        p.setHP(300);
        when(service.addRPGFigurejkk(p)).thenReturn(0L);
        this.mockMvc.perform(post("/RPGFigures")
                .content("{\"name\":\"Runic Golem\"," +
                        "\"hp\":\"300\"}")
                .contentType("application/json"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,\"name\":\"Runic Golem\",\"hp\":300,\"owner\":null}"));
        p.setId(0L);
        verify(service).addRPGFigurejkk(p);
    }

    @Test
    public void deleteRPGFigureTest() throws Exception {
        RPGFigure p = new RPGFigure();
        p.setId(1L);
        p.setName("Runic Golem");
        p.setHP(300);
        when(service.findRPGFigureById(1L)).thenReturn(p);
        this.mockMvc.perform(delete("/RPGFigure/" + p.getId()
        )
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        verify(service).deleteRPGFigure(p);

    }

    @Test
    public void getRPGFigureTest() throws Exception {
        RPGFigure p = new RPGFigure();
        p.setId(1L);
        p.setName("Runic Golem");
        p.setHP(300);
        when(service.findRPGFigureById(1L)).thenReturn(p);
        this.mockMvc.perform(get("/RPGFigure/" + p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Runic Golem\",\"hp\":300,\"owner\":null}"));
        verify(service).findRPGFigureById(1L);

    }

    @Test
    public void putRPGFigureTest() throws Exception {
        RPGFigure p = new RPGFigure();
        p.setId(1L);
        p.setName("Runic Golem");
        p.setHP(300);
        this.mockMvc.perform(put("/RPGFigure")
                .content("{\"id\":1,\"name\":\"Skeleton\",\"hp\":300,\"owner\":null}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        p.setName("Skeleton");
        verify(service).updateRPGFigure(p);

    }
}