package com.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локатор для списка напитков (ищем select, следующий за текстом вопроса)
    private By drinkSelect = By.xpath("//*[contains(text(),'What is your favorite drink?')]/following::select[1]");
    // Локатор для списка цветов (аналогично)
    private By colorSelect = By.xpath("//*[contains(text(),'What is your favorite color?')]/following::select[1]");

    // Остальные элементы формы
    private By nameField = By.xpath("//input[@type='text' and (@name='name' or @placeholder='Name')]");
    private By passwordField = By.cssSelector("input[type='password']");
    private By emailField = By.xpath("//input[@type='email'] | //label[contains(text(),'Email')]/following::input[1]");
    private By messageField = By.xpath("//textarea");
    private By submitButton = By.xpath("//button[contains(text(),'Submit')]");

    // Локатор для радио-кнопок "Do you like automation?"
    private By automationRadioGroup = By.xpath("//*[contains(text(),'Do you like automation')]/following::input[@type='radio']");

    public FormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setName(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        element.clear();
        element.sendKeys(name);
    }

    public void setPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void selectFavoriteDrink(String... drinks) {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(drinkSelect));
        Select drinkDropdown = new Select(selectElement);
        for (String drink : drinks) {
            boolean found = false;
            for (WebElement option : drinkDropdown.getOptions()) {
                if (option.getText().trim().equalsIgnoreCase(drink.trim())) {
                    drinkDropdown.selectByVisibleText(option.getText());
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new NoSuchElementException("Cannot locate option with text: " + drink);
            }
        }
    }

    public void selectFavoriteColor(String color) {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(colorSelect));
        Select colorDropdown = new Select(selectElement);
        colorDropdown.selectByVisibleText(color);
    }

    public void selectAutomationOption(String optionValue) {
        java.util.List<WebElement> radios = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(automationRadioGroup));
        for (WebElement radio : radios) {
            String value = radio.getAttribute("value");
            String id = radio.getAttribute("id");
            if ((value != null && value.equalsIgnoreCase(optionValue)) ||
                    (id != null && id.equalsIgnoreCase(optionValue))) {
                radio.click();
                return;
            }
        }
        throw new NoSuchElementException("Radio button with option " + optionValue + " not found.");
    }

    public void setEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        element.clear();
        element.sendKeys(email);
    }

    public void setMessage(String message) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(messageField));
        element.clear();
        element.sendKeys(message);
    }

    public void clickSubmit() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        element.click();
    }

    public Alert getAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }
}
