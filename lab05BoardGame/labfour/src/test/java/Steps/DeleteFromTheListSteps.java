package Steps;

import cucumber.api.java8.En;
import org.junit.Assert;
import pl.kozyra.tau.DAO.FigureDao;
import pl.kozyra.tau.Domain.RPGfigure;

import java.util.List;
import java.util.Optional;

public class DeleteFromTheListSteps implements En {

    private FigureDao dao;
    private List<RPGfigure> figures;
    private int skeletonHp;

    public DeleteFromTheListSteps() {
        Given("^Someone is in front of figure menage panel$", () -> {
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

            figure = new RPGfigure();
            figure.setId(11);
            figure.setName("skeleton");
            figure.setHP(200);
            dao.save(figure);
        });
        When("^Someone click delete button under the skeleton name$", () -> {
        });
        And("^Skeleton HP is equal to \"([^\"]*)\"$", (String hp) -> {
            skeletonHp = Integer.parseInt(hp);
        });
        Then("^\"([^\"]*)\" has been successfully deleted from the list$", (String name) -> {
            RPGfigure figure = new RPGfigure();
            figure.setId(10);
            figure.setHP(skeletonHp);
            figure.setName(name);
            dao.delete(figure);
            Assert.assertEquals(11,dao.getAll().size());
        });
        But("^Someone should not delete \"([^\"]*)\" with HP equal to \"([^\"]*)\"$", (String name, String hp) -> {
            RPGfigure figure = new RPGfigure();
            figure.setId(11);
            figure.setName(name);
            figure.setHP(Integer.parseInt(hp));
            Assert.assertEquals(figure, dao.get(11L).get());
        });

    }
}
