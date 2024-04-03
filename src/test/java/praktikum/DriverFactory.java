package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverFactory {
    private WebDriver driver;

    public void initDriver() {
        if ("firefox" .equals(System.getProperty("browser"))) {
            initFirefox();
        }
        else {
            initChrome();
        }
    }

    private void initFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    private void initChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public WebDriver getDriver(){
        return driver;
    }
}
