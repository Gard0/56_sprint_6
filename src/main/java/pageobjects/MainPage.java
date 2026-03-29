package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

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

    // Текст аккордиона - панели (раскрывающиеся элементы)
    private final By accordionPanels = By.xpath("//div[@class='accordion__panel']");

    // Родитель аккордиона
    private final By accordionItem = By.xpath("//div[@class='accordion__item']");

    // Логотип Самоката
    private final By scooterLogo = By.xpath("//img[@alt='Логотип']");

    // Логотип Яндекса
    private final By yandexLogo = By.xpath("//a[contains(@href, 'yandex.ru')]//img");

    // Кнопка закрытия cookie consent баннера
    private final By cookieCloseButton = By.xpath("//button[text()='Да все привыкли']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Нажать на кнопку "Заказать" вверху
    public void clickOrderButtonTop() {
        closeCookieConsent();
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop)).click();
    }

    // Нажать на кнопку "Заказать" внизу
    public void clickOrderButtonBottom() {
        closeCookieConsent();
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonBottom));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(button));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    // Закрыть cookie consent баннер
    private void closeCookieConsent() {
        try {
            var closeButton = driver.findElements(cookieCloseButton);
            if (!closeButton.isEmpty()) {
                closeButton.get(0).click();
            }
        } catch (Exception e) {
            // Cookie баннер может не быть, это нормально
        }
    }

    // Нажать на конкретный элемент аккордиона по индексу
    public void clickAccordionItem(int index) {
        var items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionItems));
        WebElement element = items.get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        // Ждем, пока панель визуализируется после щелчка
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Проверить, что текст аккордиона видим (элемент развернулся)
    public boolean isAccordionTextVisible() {
        try {
            var panels = driver.findElements(accordionPanels);
            // Проверяем, если есть хотя бы одна видимая панель
            for (WebElement panel : panels) {
                if (panel.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // Получить текст раскрытого аккордиона
    public String getAccordionText(int index) {
        var panels = driver.findElements(accordionPanels);
        if (index < panels.size()) {
            return panels.get(index).getText();
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
