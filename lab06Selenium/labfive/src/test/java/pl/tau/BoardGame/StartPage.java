package pl.tau.BoardGame;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class StartPage {
    private final WebDriver driver;

    @FindBy(xpath = "//*[@id=\"home-page-tabs\"]/li[1]/a")
    WebElement popularLink;

    public StartPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getPopularLink() {
        return popularLink;
    }
    public void clickPopular() {
        getPopularLink().click();
    }

    public List<WebElement> getProducts() {
        return driver.findElement(By.cssSelector("#homefeatured")).findElements(By.tagName("li"));
    }

    public void open() {
        driver.get("http://automationpractice.com");
        PageFactory.initElements(driver, this);
    }
}