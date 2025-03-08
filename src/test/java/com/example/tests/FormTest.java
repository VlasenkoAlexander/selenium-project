package com.example.tests;

import com.example.pages.FormPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FormTest {
    private WebDriver driver;
    private FormPage formPage;

    // Пример списка инструментов для дополнительного условия
    private List<String> automationTools = Arrays.asList("Selenium", "Playwright", "Cypress", "Appium", "Katalon Studio");

    @BeforeAll
    public void setUp() {
        // Укажите корректный путь к chromedriver.exe
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hativ\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void navigateToPage() {
        driver.get("https://practice-automation.com/form-fields/");
        formPage = new FormPage(driver);
    }

    @Test
    public void testFormSubmission() {
        formPage.setName("John Doe");
        formPage.setPassword("password123");
        // Выбираем опции для напитков (например, Milk и Coffee)
        formPage.selectFavoriteDrink("Milk", "Coffee");
        formPage.selectFavoriteColor("Yellow");
        formPage.selectAutomationOption("Yes");
        formPage.setEmail("name@example.com");

        // Формируем текст сообщения согласно условиям:
        // - Указываем количество инструментов.
        // - Дополнительно пишем название инструмента с наибольшим количеством символов.
        String messagePart1 = "Количество инструментов: " + automationTools.size();
        String longestTool = automationTools.stream().max((s1, s2) -> Integer.compare(s1.length(), s2.length())).orElse("");
        String messagePart2 = " Инструмент с наибольшим количеством символов: " + longestTool;
        String fullMessage = messagePart1 + messagePart2;
        formPage.setMessage(fullMessage);

        formPage.clickSubmit();

        Alert alert = formPage.getAlert();
        String alertText = alert.getText();
        assertEquals("Message received!", alertText, "Алерт должен содержать сообщение 'Message received!'");
        alert.accept();
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
