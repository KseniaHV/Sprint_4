package pom;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Order {
    private final WebDriver driver;
    
    //Локатор кнопки куки
    private final By cookie = By.id("rcc-confirm-button");

    //Локатор кнопки Заказать
    private final By buttonOrder = By.className("Button_Button__ra12g");

    //Локатор поля Имя
    private final By fieldName = By.xpath(".//input[@placeholder='* Имя']");

    //Локатор поля Фамилия
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']"); 
    
    //Локатор поля Адресс
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']"); 
    
    //Локатор поля Метро
    private final By fieldMetro = By.className("select-search__input");

    //Локатор выбора станции Метро
    private final By metroStation = By.className("select-search__row");
    
    //Локатор поля номера Телефона
    private final By numberField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    //Локатор кнопки Далее
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    //Локатор поля Когда привезти самокат
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    //Локатор выбора даты в календаре
    private final By selectDate = By.className("react-datepicker__day");

    //Локатор кнопки выбора следующего месяца в календаре
    private final By nextMonthButton  = By.className("react-datepicker__navigation--next");
    
    //Локатор поля Срока аренды
    private final By dropDownList = By.className("Dropdown-placeholder");

    //Локатор выбора срока аренды
    private final By selectRental = By.className("Dropdown-option");
    
    //Локатор поля Выбора цвета
    private final By colorSelection = By.xpath(".//label[text()='чёрный жемчуг']");

    //Локатор поля Комментарий
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    //Локатор кнопки Заказать
    private final By buttonDesign = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') " +
            "and text()='Заказать']");

    //Локатор кнопки Да
    private final By confirmationOrder = By.xpath(".//button[text()='Да']");

    //Локатор окна Заказ оформлен
    private final By successScreen = By.xpath(".//div[text()='Заказ оформлен']");

    public Order(WebDriver driver){
        this.driver = driver;
    }
    public void openPage() {
        driver.get(EnvConfig.BASE_URL);
    }
    public void acceptCookies() {
        // Проверяем, существует ли элемент
        List<WebElement> cookiesButton = driver.findElements(cookie);
     
        if (!cookiesButton.isEmpty()) {
            cookiesButton.get(0).click();
        }
    }

    public void ordering(int orderButtonIndex, String name, String lastName, String address, int metroIndex,
                         String number, int dateIndex, int rentalIndex, String comment) {
        clickOrderButton(orderButtonIndex);
        inputName(name);
        inputLastName(lastName);
        inputAddress(address);
        selectMetro(metroIndex);
        inputNumber(number);
        clickButtonNext();
        selectDeliveryDate(dateIndex);
        selectRentalPeriod(rentalIndex);
        selectColor();
        inputComment(comment);
        checkout();
        clickButtonYes();
    }
    public void clickOrderButton(int orderButtonIndex) {

        // Находим все кнопки по классу
        List<WebElement> orderButtons = driver.findElements(buttonOrder);
        if (orderButtons.size() > orderButtonIndex) {
            WebElement buttonToClick = orderButtons.get(orderButtonIndex);

            // Прокручиваем до элемента
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView" +
                    "({behavior: 'smooth', block: 'center'});", buttonToClick);

            // Ждем, пока кнопка станет кликабельной
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT));
            WebElement clickableButton = wait.until(ExpectedConditions.elementToBeClickable(buttonToClick));

            // Кликаем по кнопке
            clickableButton.click();
        } else {
            throw new IllegalArgumentException("Индекс кнопки выходит за пределы доступных кнопок. Индекс: "
                    + orderButtonIndex);
        }
    }
    public void inputName(String name) {
        driver.findElement(fieldName).sendKeys(name);
    }
    public void inputLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }
    public void inputAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }
    public void selectMetro(int metroIndex) {
        driver.findElement(fieldMetro).click();
        List<WebElement> metroStations = driver.findElements(metroStation);
        WebElement buttonMetro = metroStations.get(metroIndex);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", buttonMetro);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.DEFAULT_TIMEOUT)); //
              wait.until(ExpectedConditions.elementToBeClickable(buttonMetro));

        buttonMetro.click();
    }
    public void inputNumber(String number) {
        driver.findElement(numberField).sendKeys("+" + number);
    }
    public void clickButtonNext() {
        driver.findElement(nextButton).click();
    }

    public void selectDeliveryDate(int dateIndex) {
        driver.findElement(dateField).click();
        driver.findElement(nextMonthButton).click();
        List<WebElement> dates = driver.findElements(selectDate);
        WebElement date = dates.get(dateIndex);
        date.click();
    }
    public void selectRentalPeriod( int rentalIndex) {
        driver.findElement(dropDownList).click();
        List<WebElement> rentals = driver.findElements(selectRental);
        WebElement rental = rentals.get(rentalIndex);
        rental.click();
    }
    public void selectColor() {
        driver.findElement(colorSelection).click();
    }
    public void inputComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }
    public void checkout() {
        driver.findElement(buttonDesign).click();
    }
    public void clickButtonYes() {
        driver.findElement(confirmationOrder).click();
    }
    public void checkSuccessScreen() {
            // Получаем текст элемента
        String actualText = driver.findElement(successScreen).getText();
            // Проверяем, начинается ли текст с ожидаемого значения
        boolean isTextCorrect = actualText.startsWith("Заказ оформлен");
            // Утверждаем, что текст начинается с ожидаемой строки
        Assert.assertTrue("Текст не соответствует ожидаемому началу.", isTextCorrect);
    }
}
