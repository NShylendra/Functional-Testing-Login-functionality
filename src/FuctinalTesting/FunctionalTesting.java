package FuctinalTesting;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FunctionalTesting {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public void login(String username, String password) throws InterruptedException {
        driver.get("https://demo.dealsdray.com/");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(username);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
      
        
    }

    @Test(priority = 0)
    public void positiveScenario() throws InterruptedException {
        login("prexo.mis@dealsdray.com", "prexo.mis@dealsdray.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div/div")));
        boolean msg = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div/div")).isDisplayed();
        System.out.println(msg);
    }

    @Test(priority = 1)
    public void negativeScenario() throws InterruptedException {
        login("nshylendra@gmail.com", "nshylendra@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Wrong username or password']")));
        String errorMessage = driver.findElement(By.xpath("//p[text()='Wrong username or password']")).getText();
        Assert.assertEquals(errorMessage, "Wrong username or password");
        System.out.println(errorMessage);
    }

    @Test(priority = 2)
    public void passwordErrorValidation() throws InterruptedException {
        driver.get("https://demo.dealsdray.com/");
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("nshylendra@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mui-2-helper-text")));
        String errorMessage = driver.findElement(By.id("mui-2-helper-text")).getText();
        Assert.assertEquals(errorMessage, "this field is required");
    }

    @Test(priority = 3)
    public void loginWithEnterKeyword() throws InterruptedException  {
    	login("prexo.mis@dealsdray.com", "prexo.mis@dealsdray.com");
        Actions ac = new Actions(driver);
        ac.sendKeys(Keys.ENTER).build().perform();
        wait.until(ExpectedConditions.urlToBe("https://demo.dealsdray.com/mis/dashboard"));
    }
}
