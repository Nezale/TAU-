package Jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.kozyra.tau.DAO.FigureDao;
import pl.kozyra.tau.Domain.RPGfigure;

import java.util.List;

public class SearchByRegexTest {

    private FigureDao dao;
    private List<RPGfigure> figures;

    @Given("Someone is in front of the search bar")
    public void someoneIsInFrontOfTheSearchBar(){
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
    }

    @When("Someone sets $gol phrase into the search bar")
    public void someoneSetsGolPhraseIntoTheSearchBar(String regex){
        figures = dao.getByRegex(regex);
    }

    @Then("Someone finds the figure he wanted to find and he/she is very happy :)")
    public void someoneFindsTheFigure(){
        Assert.assertEquals(10,figures.size());
    }
}
