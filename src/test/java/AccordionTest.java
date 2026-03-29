import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccordionTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void testAccordionItemOpening(int itemIndex) {
        // Нажать на элемент аккордиона
        mainPage.clickAccordionItem(itemIndex);

        // Проверить, что текст раскрывается
        assertTrue(mainPage.isAccordionTextVisible(),
                "Аккордион элемент " + itemIndex + " должен открываться");
    }

    @Test
    public void testFirstAccordionItemOpens() {
        // Нажать на первый элемент аккордиона
        mainPage.clickAccordionItem(0);

        // Проверить видимость текста
        assertTrue(mainPage.isAccordionTextVisible(), "Первый элемент аккордиона должен открыться");
    }

    @Test
    public void testSecondAccordionItemOpens() {
        // Нажать на второй элемент аккордиона
        mainPage.clickAccordionItem(1);

        // Проверить видимость текста
        assertTrue(mainPage.isAccordionTextVisible(), "Второй элемент аккордиона должен открыться");
    }
}
