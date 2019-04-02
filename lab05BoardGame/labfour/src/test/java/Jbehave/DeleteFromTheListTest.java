package Jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.kozyra.tau.DAO.FigureDao;
import pl.kozyra.tau.Domain.RPGfigure;

import java.util.List;

public class DeleteFromTheListTest {
    private FigureDao dao;
    private List<RPGfigure> figures;
    private RPGfigure figure;

    @Given("Someone is in front of figure menage panel")
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

    @When("Someone click delete button under the $skeleton name")
    public void someoneSetsGolPhraseIntoTheSearchBar(String skeleton){
        figure = new RPGfigure();
        figure.setId(10);
        figure.setName(skeleton);
        figure.setHP(50);
        dao.delete(figure);
    }

    @Then("Then  skeleton has been successfully deleted from the list")
    public void someoneFindsTheFigure(){
        System.out.println("Expected size is: " + figures.size());
        Assert.assertEquals(9,dao.getAll().size());
    }
}
