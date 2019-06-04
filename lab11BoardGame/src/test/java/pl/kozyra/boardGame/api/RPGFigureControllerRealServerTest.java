package pl.kozyra.boardGame.api;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kozyra.boardGame.domain.RPGFigure;
import pl.kozyra.boardGame.service.RPGFigureManager;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * This example creates real server that will handle requests. The spring test will
 * query the server and tests checks the correctness of responses
 */

@Ignore
@RunWith(SpringRunner.class)
@ComponentScan({"pl.library"})
@PropertySource("classpath:jdbc.properties")
@ImportResource({"classpath:/beans.xml"})
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RPGFigureControllerRealServerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private RPGFigureController controller;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    RPGFigureManager libraryManager; // manager is needed for direct manipulation with database

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/",
                        String.class)).contains("Hello");
    }

    @Test
    public void getAllShouldReturnSomeResultsFromDatabase() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/RPGFigures",
                        List.class)).isNotNull();
    }

    @Test
    public void getAllShouldReturnResultsThatWerePreviouslyPutIntoDatabase() throws Exception {
        RPGFigure newRPGFigure = new RPGFigure();
        newRPGFigure.setName("Restowy Rester");
        Long newId = libraryManager.addRPGFigurejkk(newRPGFigure);
        List<java.util.LinkedHashMap> personsFromRest =
                this.restTemplate.getForObject("http://localhost:" + port + "/RPGFigures", List.class);
        boolean found = false;
        for (LinkedHashMap p: personsFromRest) {
            if (p.get("id").toString().equals(newId.toString())) found = true;
        }
        assertTrue(found);
    }
}
