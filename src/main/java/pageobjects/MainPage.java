package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.concurrent.TimeUnit;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы главной страницы
    // Кнопка "Заказать" вверху страницы
    private final By orderButtonTop = By.xpath("(//button[contains(text(), 'Заказать')])[1]");

    // Кнопка "Заказать" внизу страницы
    private final By orderButtonBottom = By.xpath("(//button[contains(text(), 'Заказать')])[2]");

    // Стрелочки аккордиона (все элементы)
    private final By accordionItems = By.xpath("//div[@class='accordion']//div[@class='accordion__heading']");

    // Текст аккордиона - используется для проверки открытия
    private final By accordionText = By.xpath("//div[@class='accordion__panel']");

    // Логотип Самоката
    private final By scooterLogo = By.xpath("//img[@alt='Логотип']");

    // Логотип Яндекса
    private final By yandexLogo = By.xpath("//a[contains(@href, 'yandex.ru')]//img");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Нажать на кнопку "Заказать" вверху
    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop)).click();
    }

    // Нажать на кнопку "Заказать" внизу
    public void clickOrderButtonBottom() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom)).click();
    }

    // Нажать на конкретный элемент аккордиона по индексу
    public void clickAccordionItem(int index) {
        var items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionItems));
        items.get(index).click();
    }

    // Проверить, что текст аккордиона видим (элемент развернулся)
    public boolean isAccordionTextVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(accordionText));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Получить текст раскрытого аккордиона
    public String getAccordionText(int index) {
        var items = driver.findElements(accordionText);
        if (index < items.size()) {
            return items.get(index).getText();
        }
        return "";
    }

    // Нажать на логотип Самоката
    public void clickScooterLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(scooterLogo)).click();
    }

    // Нажать на логотип Яндекса
    public void clickYandexLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(yandexLogo)).click();
    }
}
