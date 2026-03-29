package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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

    // Чекбоксы выбора цвета самоката (берём первый доступный)
    private final By colorCheckboxes = By.cssSelector("input[type='checkbox']");

    // Чекбокс "Принимание условий" - ищем чекбокс с типом checkbox
    private final By checkboxTerms = By.xpath("//label//input[@type='checkbox'] | //input[@type='checkbox']//ancestor::label");

    // Кнопка "Заказать" - более универсальный селектор
    private final By orderSubmitButton = By.xpath(
            "/html/body/div/div/div[2]/div[3]/button[2]"
                    + " | //button[contains(normalize-space(),'Заказать')]");

    // Кнопка "Далее" в первой части формы
    private final By nextButton = By.xpath("//*[@id='root']/div/div[2]/div[3]/button");

    // Сообщение об успешном создании заказа
    private final By successMessage = By.xpath(
            "//*[contains(@class,'Order_ModalHeader') or contains(text(),'Заказ оформлен') or contains(text(),'Заказ создан')]");
    // Модалка подтверждения и кнопка "Да"
    private final By confirmModal = By.xpath("//*[contains(text(),'Хотите оформить заказ')]");
    private final By confirmYesButton = By.xpath("/html/body/div/div/div[2]/div[5]/div[2]/button[2]"
            + " | //button[contains(normalize-space(),'Да')]"
            + " | //button[contains(translate(.,'YES','yes'),'yes')]"
            + " | //button[contains(@class,'Order_Buttons__1xGrp')][last()]");

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
            By optionLocator = By.xpath("//div[contains(@class,'Dropdown-option')][contains(.,'" + duration + "')]");
            WebElement option;
            try {
                option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
            } catch (Exception e) {
                option = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Dropdown-menu .Dropdown-option")));
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
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
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(orderSubmitButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            wait.until(ExpectedConditions.elementToBeClickable(button));
            try {
                button.click();
            } catch (Exception clickEx) {
                System.out.println("[" + methodName + "] direct click failed, trying JS");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            }
            // ждём быструю реакцию: либо модалка, либо редирект
            boolean appeared = false;
            try {
                new WebDriverWait(driver, Duration.ofSeconds(8)).until(
                        ExpectedConditions.or(
                                ExpectedConditions.presenceOfElementLocated(confirmYesButton),
                                ExpectedConditions.urlContains("/track")));
                appeared = true;
            } catch (Exception ignore) {
                // ничего не появилось — попробуем повторно кликнуть через JS
                try {
                    ((JavascriptExecutor) driver).executeScript(
                            "const btn=document.evaluate(\"/html/body/div/div/div[2]/div[3]/button[2]\",document,null,XPathResult.FIRST_ORDERED_NODE_TYPE,null).singleNodeValue; if(btn){btn.click(); return true;} const cand=document.querySelectorAll('button'); for(const b of cand){if(/заказать/i.test(b.textContent)){b.click(); return true;}} return false;");
                    new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                            ExpectedConditions.presenceOfElementLocated(confirmYesButton));
                    appeared = true;
                } catch (Exception ignored) {
                    appeared = false;
                }
            }
            System.out.println("[" + methodName + "] Order submit clicked, modal appeared=" + appeared);
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("submitOrder failed", e);
        }
    }

    // Выбрать любой цвет самоката (первый чекбокс)
    public void selectFirstColor() {
        String methodName = "selectFirstColor";
        System.out.println("[" + methodName + "] Selecting first color");
        try {
            WebElement color = wait.until(ExpectedConditions.elementToBeClickable(colorCheckboxes));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", color);
            if (!color.isSelected()) {
                color.click();
            }
            System.out.println("[" + methodName + "] Color selected");
        } catch (Exception e) {
            System.out.println("[" + methodName + "] ERROR: " + e.getMessage());
            // не критично, продолжаем без цвета
        }
    }

    // Подтвердить создание заказа в модальном окне
        // ÐŸÐ¾Ð´Ñ‚Ð²ÐµÑ€Ð´Ð¸Ñ‚ÑŒ ÑÐ¾Ð·Ð´Ð°Ð½Ð¸Ðµ Ð·Ð°ÐºÐ°Ð·Ð° Ð² Ð¼Ð¾Ð´Ð°Ð»ÑŒÐ½Ð¾Ð¼ Ð¾ÐºÐ½Ðµ
    public void confirmOrder() {
        String methodName = "confirmOrder";
        System.out.println("[" + methodName + "] Confirming order");
        try {
            WebElement yesButton = new WebDriverWait(driver, Duration.ofSeconds(12))
                    .until(ExpectedConditions.presenceOfElementLocated(confirmYesButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yesButton);
            try {
                yesButton.click();
                System.out.println("[" + methodName + "] Confirmation clicked directly");
            } catch (Exception clickEx) {
                System.out.println("[" + methodName + "] Direct click failed, trying JS");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesButton);
            }
        } catch (Exception e) {
            System.out.println("[" + methodName + "] Primary confirmation locator not found, attempting JS scan");
            try {
                Object clicked = ((JavascriptExecutor) driver).executeScript(
                        "const btn = Array.from(document.querySelectorAll('button')).find(b => /\\b\u0434\u0430\\b/i.test(b.textContent));\n" +
                        "if (btn) { btn.click(); return true; }\n" +
                        "return false;");
                if (Boolean.TRUE.equals(clicked)) {
                    System.out.println("[" + methodName + "] Confirmation clicked via JS fallback");
                } else {
                    System.out.println("[" + methodName + "] Confirmation modal still not found, continuing without it");
                }
            } catch (Exception ignore) {
                System.out.println("[" + methodName + "] Confirmation modal not found, continuing without it");
            }
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(35))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(successMessage),
                            ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Посмотреть')]")),
                            ExpectedConditions.urlContains("/track")
                    ));
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




