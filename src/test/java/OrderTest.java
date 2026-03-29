import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        String browser = System.getProperty("browser", "firefox").toLowerCase();
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
        }
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
            "Иван,Петров,Москва улица Ленина 5,Черкизовская,+79991234567,23.04.2026,сутки",
            "Мария,Иванова,Санкт-Петербург Невский 10,Балтийская,+79161234567,30.03.2026,двое суток"
    })
    public void testOrderFlowFromTopButton(String name, String lastName, String address,
                                            String metro, String phone, String date, String duration) {
        // Нажать на кнопку "Заказать" вверху
        mainPage.clickOrderButtonTop();

        // Дождаться загрузки модального окна формы
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Инициализировать страницу заказа
        orderPage = new OrderPage(driver);

        // === ПЕРВАЯ ЧАСТЬ ФОРМЫ ===
        // Заполнить первую часть формы
        orderPage.fillName(name);
        orderPage.fillLastName(lastName);
        orderPage.fillAddress(address);
        orderPage.fillMetro(metro);
        orderPage.fillPhone(phone);

        // Нажать кнопку "Далее"
        orderPage.clickNextButton();

        // Дождаться загрузки второй части формы
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // === ВТОРАЯ ЧАСТЬ ФОРМЫ ===
        // Заполнить вторую часть формы
        orderPage.fillDeliveryDate(date);
        orderPage.selectRentalDuration(duration);
        orderPage.selectFirstColor();
        orderPage.acceptTerms();

        // Отправить форму
        orderPage.submitOrder();
        orderPage.confirmOrder();

        // Проверить успешное создание заказа
        assertTrue(orderPage.isSuccessMessageDisplayed(),
                "Должно отобразиться сообщение об успешном создании заказа");
    }

    @ParameterizedTest
    @CsvSource({
            "Иван,Петров,Москва улица Ленина 5,Черкизовская,+79991234567,23.04.2026,сутки",
            "Мария,Иванова,Санкт-Петербург Невский 10,Балтийская,+79161234567,30.03.2026,двое суток"
    })
    public void testOrderFlowFromBottomButton(String name, String lastName, String address,
                                               String metro, String phone, String date, String duration) {
        // Нажать на кнопку "Заказать" внизу
        mainPage.clickOrderButtonBottom();

        // Дождаться загрузки модального окна формы
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Инициализировать страницу заказа
        orderPage = new OrderPage(driver);

        // === ПЕРВАЯ ЧАСТЬ ФОРМЫ ===
        // Заполнить первую часть формы
        orderPage.fillName(name);
        orderPage.fillLastName(lastName);
        orderPage.fillAddress(address);
        orderPage.fillMetro(metro);
        orderPage.fillPhone(phone);

        // Нажать кнопку "Далее"
        orderPage.clickNextButton();

        // Дождаться загрузки второй части формы
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // === ВТОРАЯ ЧАСТЬ ФОРМЫ ===
        // Заполнить вторую часть формы
        orderPage.fillDeliveryDate(date);
        orderPage.selectRentalDuration(duration);
        orderPage.selectFirstColor();
        orderPage.acceptTerms();

        // Отправить форму
        orderPage.submitOrder();
        orderPage.confirmOrder();

        // Проверить успешное создание заказа
        assertTrue(orderPage.isSuccessMessageDisplayed(),
                "Должно отобразиться сообщение об успешном создании заказа");
    }
}
