package Steps;

import cucumber.api.java8.En;
import org.junit.Assert;

class CheckFigure {
    static String isItSkeleton(String figure){
        if (figure.equals("skeleton")) {
            return "Yes";
        }
        return "No";
    }
}

public class ExactlyThatFigureSteps implements En {
    private String figure;
    private String answer;
    public ExactlyThatFigureSteps() {
        Given("^This is \"([^\"]*)\"$", (String figure) -> {
            this.figure = figure;
        });
        When("^I ask you this is skeleton$", () -> {
            this.answer = CheckFigure.isItSkeleton(figure);
        });
        Then("^I should tell \"([^\"]*)\"$", (String expectedAnswer) -> {
            Assert.assertEquals(expectedAnswer,answer);
        });
    }
}
