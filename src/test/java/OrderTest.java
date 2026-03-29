import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.MainPage;
import pageobjects.OrderPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
        orderPage = null;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvSource({
            "Иван,Петров,Москва улица Ленина 5,Красная площадь,+79991234567,1",
            "Мария,Сидорова,Санкт-Петербург Невский проспект 10,Невский проспект,+79997654321,2"
    })
    public void testOrderFlowFromTopButton(String name, String lastName, String address,
                                            String metro, String phone, String duration) {
        // Нажать на кнопку "Заказать" вверху
        mainPage.clickOrderButtonTop();

        // Инициализировать страницу заказа
        orderPage = new OrderPage(driver);

        // Заполнить форму первым набором данных
        orderPage.fillName(name);
        orderPage.fillLastName(lastName);
        orderPage.fillAddress(address);
        orderPage.fillMetro(metro);
        orderPage.fillPhone(phone);
        orderPage.selectRentalDuration(duration);
        orderPage.acceptTerms();

        // Отправить форму
        orderPage.submitOrder();

        // Проверить успешное создание заказа
        assertTrue(orderPage.isSuccessMessageDisplayed(),
                "Должно отобразиться сообщение об успешном создании заказа");
    }

    @ParameterizedTest
    @CsvSource({
            "Иван,Петров,Москва улица Ленина 5,Красная площадь,+79991234567,1",
            "Мария,Сидорова,Санкт-Петербург Невский проспект 10,Невский проспект,+79997654321,2"
    })
    public void testOrderFlowFromBottomButton(String name, String lastName, String address,
                                               String metro, String phone, String duration) {
        // Нажать на кнопку "Заказать" внизу
        mainPage.clickOrderButtonBottom();

        // Инициализировать страницу заказа
        orderPage = new OrderPage(driver);

        // Заполнить форму первым набором данных
        orderPage.fillName(name);
        orderPage.fillLastName(lastName);
        orderPage.fillAddress(address);
        orderPage.fillMetro(metro);
        orderPage.fillPhone(phone);
        orderPage.selectRentalDuration(duration);
        orderPage.acceptTerms();

        // Отправить форму
        orderPage.submitOrder();

        // Проверить успешное создание заказа
        assertTrue(orderPage.isSuccessMessageDisplayed(),
                "Должно отобразиться сообщение об успешном создании заказа");
    }
}
