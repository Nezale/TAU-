package pl.tau.BoardGame;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegisterPage {

    public static boolean registerpage() {

        WebDriver driver = new ChromeDriver();
        Random random = new Random();
        int rand1 = random.nextInt() *45;
        int rand2 = random.nextInt() *45;


        driver.navigate().to("http://automationpractice.com/index.php");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//*[@id='header']/div[2]/div/div/nav/div[1]/a")).click();

        driver.findElement(By.xpath("//*[@id='email_create']")).sendKeys("randomemail"+rand1 + rand2+ "@something.com");
        driver.findElement(By.xpath("//*[@id='SubmitCreate']")).click();


        String valueOfGender = "2";
        List<WebElement> RadioButtonList = driver.findElements(By.name("id_gender"));


        for (int i = 0; i < RadioButtonList.size(); i++) {
            String gend = RadioButtonList.get(i).getAttribute("value");
            if (gend.equalsIgnoreCase((valueOfGender))) {
                RadioButtonList.get(i).click();
                break;
            }

        }


        driver.findElement(By.xpath("//*[@id='customer_firstname']")).sendKeys("FirstName");
        driver.findElement(By.xpath("//*[@id='customer_lastname']")).sendKeys("lastName");
        driver.findElement(By.xpath("//*[@id='passwd']")).sendKeys("Password@123");


        Select sDate = new Select(driver.findElement(By.xpath("//*[@id='days']")));
        sDate.selectByVisibleText("2  ");

        Select sMonth = new Select(driver.findElement(By.xpath("//*[@id='months']")));
        sMonth.selectByVisibleText("May ");

        Select sYear = new Select(driver.findElement(By.xpath("//*[@id='years']")));
        sYear.selectByVisibleText("2015  ");


        String newsLetterReq = "Yes";
        if (newsLetterReq.equalsIgnoreCase(newsLetterReq)) {
            driver.findElement(By.xpath(".//*[@id='newsletter']")).click();
        }

        String reciveSpclOffer = "Yes";
        if (reciveSpclOffer.equalsIgnoreCase(reciveSpclOffer)) {
            driver.findElement(By.xpath("//*[@id='optin']")).click();


            driver.findElement(By.xpath("//*[@id='firstname']")).sendKeys("FnameInAddr");
            driver.findElement(By.xpath("//*[@id='lastname']")).sendKeys("LnameinAddr");
            driver.findElement(By.xpath("//*[@id='company']")).sendKeys("comp");
            driver.findElement(By.xpath("//*[@id='address1']")).sendKeys("addr1");
            driver.findElement(By.xpath("//*[@id='address2']")).sendKeys("addr2");
            driver.findElement(By.xpath("//*[@id='city']")).sendKeys("Pune");

            Select sState = new Select(driver.findElement(By.xpath("//*[@id='id_state']")));
            sState.selectByVisibleText("Alabama");

            driver.findElement(By.xpath("//*[@id='postcode']")).sendKeys("12345");

            Select sCountry = new Select(driver.findElement(By.xpath("//*[@id='id_country']")));
            sCountry.selectByVisibleText("United States");

            driver.findElement(By.xpath("//*[@id='other']")).sendKeys("any other info");
            driver.findElement(By.xpath("//*[@id='phone']")).sendKeys("123457876");
            driver.findElement(By.xpath("//*[@id='phone_mobile']")).sendKeys("868768768768");
            driver.findElement(By.xpath("//*[@id='alias']")).sendKeys("alias");

            driver.findElement(By.xpath("//*[@id='account-creation_form']")).click();


        }
        return true;
    }
}
