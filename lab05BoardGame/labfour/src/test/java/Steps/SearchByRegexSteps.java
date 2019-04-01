package Steps;

import cucumber.api.java8.En;
import org.junit.Assert;
import pl.kozyra.tau.DAO.FigureDao;
import pl.kozyra.tau.Domain.RPGfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchByRegexSteps implements En {

    private FigureDao dao;
    private List <RPGfigure> figures;

    public SearchByRegexSteps() {
        Given("^Someone is in front of the search bar$", () -> {
            dao = new FigureDao();
            for(int i = 0; i<10; i++){
                RPGfigure figure = new RPGfigure();
                figure.setId(i);
                figure.setName("Runic golem");
                figure.setHP(500);
                dao.save(figure);
            }
            RPGfigure figure = new RPGfigure();
            figure.setId(10);
            figure.setName("skeleton");
            figure.setHP(50);
            dao.save(figure);

        });
        When("^Someone put \"([^\"]*)\" phrase into the search bar$", (String regex) -> {
            Assert.assertEquals(10, dao.getByRegex("gol").size());
        });
        Then("^Someone finds the figure he wanted to find and he/she is very happy :\\)$", () -> {
        });
    }
}
