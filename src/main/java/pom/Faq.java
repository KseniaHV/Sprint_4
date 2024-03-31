package pom;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class Faq {
    private final WebDriver driver;
    private final By cookie = By.id("rcc-confirm-button");
    private final String accordionItem = "accordion__heading-"; // Локатор вопроса
    private final String answer = "accordion__panel-";  // Локатор ответа
    public Faq(WebDriver driver){
        this.driver = driver;
    }
    public void openWebsite() {
        driver.get(EnvConfig.BASE_URL);
    }
    public void clickOnQuestion(int itemId) {
        String dynamicItemId = accordionItem + itemId;

        WebElement element = driver.findElement(By.id(dynamicItemId));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT));
                wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    public void checkFaq(int itemId, String expectedAnswer) {

        String foundAnswerId = answer + itemId;

        String foundAnswer = driver.findElement(By.id(foundAnswerId)).getText();

        Assert.assertEquals(expectedAnswer, foundAnswer);
    }
}
