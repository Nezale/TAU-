package pl.tau.BoardGame.selenium;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import pl.tau.BoardGame.LoginPage;
import pl.tau.BoardGame.RegisterPage;
import pl.tau.BoardGame.StartPage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SeleniumTest {
    private static WebDriver driver;
    private StartPage startPage;
    private LoginPage loginPage;

    @BeforeClass
    public static void driverSetup() {
        System.setProperty(
                "webdriver.chrome.driver",
                "wb/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts()
                .implicitlyWait(1, TimeUnit.MICROSECONDS);
    }

    @Before
    public void before() {
        startPage = new StartPage(driver);
        loginPage = new LoginPage(driver);
        driver.manage().window().fullscreen();

    }


    @Test
    public void popularCount() {
        startPage.open();
        startPage.clickPopular();
        assertEquals(7,
                startPage.getProducts().size());
    }

    @Test
    public void loginIncorrectSmall() {
        driver.manage().window().setSize(new Dimension(300, 700));
        loginPage.open();
        loginPage.login();
        assertFalse(loginPage.isLoginFailed());
    }

    @Test
    public void loginIncorrect() {
        loginPage.open();
        loginPage.login();
        assertFalse(loginPage.isLoginFailed());
    }

    @Test
    public void loginSuccessfull(){
        loginPage.open();
        loginPage.loginSuccess();
        assertEquals("http://automationpractice.com/index.php?controller=my-account", loginPage.isLoginSuccessfull());
    }

    @Test
    public void registerUser() throws IOException {

        assertTrue(RegisterPage.registerpage());
    }

    @AfterClass
    public static void cleanp() {
        driver.quit();
    }
}
