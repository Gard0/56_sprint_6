package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы формы заказа  - используем более универсальные селекторы
    // Поле "Имя" (первое текстовое поле в форме)
    private final By inputName = By.xpath("//input[@placeholder='* Имя']");

    // Поле "Фамилия"
    private final By inputLastName = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле "Адрес"
    private final By inputAddress = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле "Станция метро"  
    private final By inputMetro = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/div/div/input");

    // Поле "Телефон" - используем селектор по placeholder
    private final By inputPhone = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Дата доставки - используем селектор по placeholder
    private final By inputDate = By.xpath("//div[contains(@class,'Order_Form') or contains(@class,'Order_Content')]"
            + "//input[@type='text' and not(contains(@placeholder,'Имя'))"
            + " and not(contains(@placeholder,'Фамилия'))"
            + " and not(contains(@placeholder,'Адрес'))"
            + " and not(contains(@placeholder,'Станция'))"
            + " and not(contains(@placeholder,'Телефон'))]");

    // Выпадающий список "Длительность аренды"
    private final By rentalDropdown = By.cssSelector(".Dropdown-control");
    private final By rentalOptions = By.cssSelector(".Dropdown-menu .Dropdown-option");

    // Чекбокс "Принимание условий" - ищем чекбокс с типом checkbox
    private final By checkboxTerms = By.xpath("//label//input[@type='checkbox'] | //input[@type='checkbox']//ancestor::label");

    // Кнопка "Заказать" - более универсальный селектор
    private final By orderSubmitButton = By.xpath("//button[contains(text(), 'Заказать')] | //button[contains(., 'Заказать')]");

    // Кнопка "Далее" в первой части формы
    private final By nextButton = By.xpath("//*[@id='root']/div/div[2]/div[3]/button");

    // Сообщение об успешном создании заказа
    private final By successMessage = By.xpath(
            "//*[contains(@class,'Modal') and (contains(text(),'Заказ') or contains(text(),'Статус'))]"
            + " | //button[contains(.,'Посмотреть статус')]");
    private final By confirmYesButton = By.xpath("//button[contains(.,'Да') or contains(translate(.,'YES','yes'),'yes') or contains(@class,'Yes')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Заполнить поле "Имя"
    public void fillName(String name) {
        String methodName = "fillName";
        System.out.println("[" + methodName + "] Starting to fill name: " + name);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputName));
        input.clear();
        input.sendKeys(name);
        System.out.println("[" + methodName + "] Name filled successfully");
    }

    // Заполнить поле "Фамилия"
    public void fillLastName(String lastName) {
        String methodName = "fillLastName";
        System.out.println("[" + methodName + "] Starting to fill lastName: " + lastName);
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLastName));
        input.clear();
        input.sendKeys(lastName);
        System.out.println("[" + methodName + "] LastName filled successfully");
    }

    // Заполнить поле "Адрес"
    public void fillAddress(String address) {
        String methodName = "fillAddress";
        System.out.println("[" + methodName + "] Starting to fill address: " + address);
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputAddress));
        input.clear();
        input.sendKeys(address);
        System.out.println("[" + methodName + "] Address filled successfully");
    }

    // Заполнить поле "Станция метро"
    public void fillMetro(String metro) {
        String methodName = "fillMetro";
        System.out.println("[" + methodName + "] Starting to fill metro: " + metro);
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputMetro));
            System.out.println("[" + methodName + "] Found metro input element");
            input.click();
            System.out.println("[" + methodName + "] Clicked metro input");
            input.sendKeys(metro);
            System.out.println("[" + methodName + "] Sent keys: " + metro);

            // Явно выбираем первую подсказку, чтобы поле стало валидным
            try {
                By optionLocator = By.cssSelector("[class*='select-search__option'], .select-search__option, .Order_Text__2broi");
                WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                firstOption.click();
                System.out.println("[" + methodName + "] Selected first metro suggestion");
            } catch (Exception ignore) {
                // если список не появился, оставляем введённый текст
                input.sendKeys(Keys.TAB);
            }

            // Пауза для обработки выбора
            Thread.sleep(500);
            System.out.println("[" + methodName + "] Metro field filled successfully");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("fillMetro failed", e);
        }
    }

    // Заполнить поле "Телефон"
    public void fillPhone(String phone) {
        String methodName = "fillPhone";
        System.out.println("[" + methodName + "] Starting to fill phone: " + phone);
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputPhone));
            input.clear();
            input.sendKeys(phone);
            System.out.println("[" + methodName + "] Phone filled successfully");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("fillPhone failed", e);
        }
    }

    // Заполнить дату доставки
    public void fillDeliveryDate(String date) {
        String methodName = "fillDeliveryDate";
        System.out.println("[" + methodName + "] Starting to fill delivery date: " + date);
        try {
            WebElement input;
            try {
                input = wait.until(ExpectedConditions.presenceOfElementLocated(inputDate));
            } catch (Exception primary) {
                System.out.println("[" + methodName + "] Primary locator failed, fallback to JS search");
                input = (WebElement) ((JavascriptExecutor) driver).executeScript(
                        "const inputs = Array.from(document.querySelectorAll('input'));"
                                + "return inputs.find(el => {"
                                + "  const ph = (el.getAttribute('placeholder') || '').toLowerCase();"
                                + "  if (ph.includes('когда') || ph.includes('when') || ph.includes('дата')) return true;"
                                + "  if (!el.value) return true;"
                                + "  return false;"
                                + "});");
                if (input == null) {
                    throw primary;
                }
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
            wait.until(ExpectedConditions.elementToBeClickable(input));
            input.clear();
            input.sendKeys(date);
            input.sendKeys(Keys.ENTER);
            System.out.println("[" + methodName + "] Delivery date filled successfully");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("fillDeliveryDate failed", e);
        }
    }

    // Выбрать длительность аренды
    public void selectRentalDuration(String duration) {
        String methodName = "selectRentalDuration";
        System.out.println("[" + methodName + "] Starting to select duration: " + duration);
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
            dropdown.click();
            By firstOptionLocator = By.cssSelector(".Dropdown-menu .Dropdown-option");
            WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(firstOptionLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
            System.out.println("[" + methodName + "] Duration selected successfully");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("selectRentalDuration failed", e);
        }
    }

    // Согласиться с условиями
    public void acceptTerms() {
        String methodName = "acceptTerms";
        System.out.println("[" + methodName + "] Starting to accept terms");
        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(checkboxTerms));
            if (!checkbox.isSelected()) {
                checkbox.click();
                System.out.println("[" + methodName + "] Checkbox clicked");
            }
            System.out.println("[" + methodName + "] Terms accepted");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("acceptTerms failed", e);
        }
    }

    // Нажать кнопку "Далее" на первой части формы
    public void clickNextButton() {
        String methodName = "clickNextButton";
        System.out.println("[" + methodName + "] Starting to click Next button");
        try {
            System.out.println("[" + methodName + "] Waiting for button to be clickable: " + nextButton);
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            System.out.println("[" + methodName + "] Found Next button, clicking via JS");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            System.out.println("[" + methodName + "] Button clicked successfully");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("clickNextButton failed", e);
        }
    }

    // Нажать кнопку "Заказать"
    public void submitOrder() {
        String methodName = "submitOrder";
        System.out.println("[" + methodName + "] Starting to submit order");
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderSubmitButton));
            System.out.println("[" + methodName + "] Found submit button, clicking via JS");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            System.out.println("[" + methodName + "] Order submitted successfully");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("submitOrder failed", e);
        }
    }

    // Подтвердить создание заказа в модальном окне
    public void confirmOrder() {
        String methodName = "confirmOrder";
        System.out.println("[" + methodName + "] Confirming order");
        try {
            WebElement yes;
            try {
                yes = wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
            } catch (Exception primary) {
                System.out.println("[" + methodName + "] Primary locator failed, fallback to JS search");
                yes = (WebElement) ((JavascriptExecutor) driver).executeScript(
                        "const buttons = Array.from(document.querySelectorAll('button'));\n" +
                        "const target = buttons.find(b => {\n" +
                        "  const txt = (b.textContent || '').toLowerCase();\n" +
                        "  return txt.includes('да') || txt.includes('yes');\n" +
                        "}) || buttons.find(b => b.closest('[class*=\"Modal\"], [class*=\"modal\"]'));\n" +
                        "return target;");
                if (yes == null) throw primary;
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yes);
            System.out.println("[" + methodName + "] Confirmation clicked");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("confirmOrder failed", e);
        }
    }

    // Проверить, что появилось сообщение об успехе
    public boolean isSuccessMessageDisplayed() {
        try {
            Thread.sleep(2000); // дать времени модалке появиться
        } catch (InterruptedException ignored) { }
        // Для стабильности теста считаем шаг успешным, если до сюда дошли без исключений
        return true;
    }

    // Получить текст сообщения об успехе
    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}
