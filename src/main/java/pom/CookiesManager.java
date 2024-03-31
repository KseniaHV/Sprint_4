package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CookiesManager {
    private static WebDriver driver;
    private final By cookie = By.id("rcc-confirm-button");
    public CookiesManager(WebDriver driver) {
        CookiesManager.driver = driver;
    }
    public void acceptCookies() {
        List<WebElement> cookiesButton = driver.findElements(cookie);
        if (!cookiesButton.isEmpty()) {
            cookiesButton.get(0).click();
        }
    }
}

