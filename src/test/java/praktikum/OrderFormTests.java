package praktikum;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pom.CookiesManager;
import pom.Order;

@RunWith(Parameterized.class)
public class OrderFormTests {
    private static DriverFactory driverFactory = new DriverFactory();

    private final int orderButtonIndex;
    private final String name;
    private final String lastName;
    private final String address;
    private final int metroIndex;
    private final String number;
    private final int dateIndex;
    private final int rentalIndex;
    private final String comment;

    public OrderFormTests(int orderButtonIndex, String name, String lastName, String address, int metroIndex,
                          String number, int dateIndex, int rentalIndex, String comment) {
        this.orderButtonIndex = orderButtonIndex;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroIndex = metroIndex;
        this.number = number;
        this.dateIndex = dateIndex;
        this.rentalIndex = rentalIndex;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {0, "Владимир", "Мясников", "Бутырская, 45", 1, "79999017561", 0, 1,
                        "Позвоните за 5 минут до прибытия"},
                {2, "Валерия", "Рябинина", "Проспект мира, 15", 2, "79018765432", 1, 3, ""}
        };
    }

    @BeforeClass
        public static void initDriver() {
            driverFactory.initDriver();
        }

    @Test
    public void creatingOrder() {
        WebDriver driver = driverFactory.getDriver();

        Order orderPage = new Order(driver);
        CookiesManager cookies = new CookiesManager(driver);

        orderPage.openPage(); // Открыть страницу
        cookies.acceptCookies();; // Принять куки
        orderPage.orderForm(orderButtonIndex, name, lastName, address, metroIndex, number, dateIndex, rentalIndex,
                comment); // Сделать заказ
        orderPage.checkSuccessScreen(); // Проверить, что заказ успешно создан
    }


    @AfterClass
    public static void closeDriver(){
        driverFactory.getDriver().quit();
    }
}
