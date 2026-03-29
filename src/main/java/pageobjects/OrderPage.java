package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы формы заказа
    // Поле "Имя"
    private final By inputName = By.xpath("//input[@placeholder='Имя']");

    // Поле "Фамилия"
    private final By inputLastName = By.xpath("//input[@placeholder='Фамилия']");

    // Поле "Адрес"
    private final By inputAddress = By.xpath("//input[@placeholder='Адрес']");

    // Поле "Станция метро"
    private final By inputMetro = By.xpath("//input[@placeholder='Станция метро']");

    // Поле "Телефон"
    private final By inputPhone = By.xpath("//input[@placeholder='Номер телефона']");

    // Дата доставки
    private final By inputDate = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    // Выпадающий список "Длительность аренды"
    private final By selectRentalDuration = By.xpath("//select[@class='Dropdown-root']");

    // Чекбокс "Принимание условий"
    private final By checkboxTerms = By.xpath("//input[@type='checkbox']");

    // Кнопка "Заказать" в форме
    private final By orderSubmitButton = By.xpath("//button[contains(text(), 'Заказать')]");

    // Сообщение об успешном создании заказа
    private final By successMessage = By.xpath("//div[contains(text(), 'Заказ')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    // Заполнить поле "Имя"
    public void fillName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputName)).sendKeys(name);
    }

    // Заполнить поле "Фамилия"
    public void fillLastName(String lastName) {
        driver.findElement(inputLastName).sendKeys(lastName);
    }

    // Заполнить поле "Адрес"
    public void fillAddress(String address) {
        driver.findElement(inputAddress).sendKeys(address);
    }

    // Заполнить поле "Станция метро"
    public void fillMetro(String metro) {
        var input = driver.findElement(inputMetro);
        input.sendKeys(metro);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@class='select-search__options']//li"))).get(0).click();
    }

    // Заполнить поле "Телефон"
    public void fillPhone(String phone) {
        driver.findElement(inputPhone).sendKeys(phone);
    }

    // Заполнить дату доставки
    public void fillDeliveryDate(String date) {
        driver.findElement(inputDate).sendKeys(date);
    }

    // Выбрать длительность аренды
    public void selectRentalDuration(String duration) {
        var select = new Select(driver.findElement(selectRentalDuration));
        select.selectByValue(duration);
    }

    // Согласиться с условиями
    public void acceptTerms() {
        driver.findElement(checkboxTerms).click();
    }

    // Нажать кнопку "Заказать"
    public void submitOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(orderSubmitButton)).click();
    }

    // Проверить, что появилось сообщение об успехе
    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Получить текст сообщения об успехе
    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}
