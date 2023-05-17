package _automation_checking;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest {
    private static FirefoxDriver driver;

    @BeforeEach
    void launchBrowser() {
        driver = new FirefoxDriver();
    }
    @Test
    void newItemSingleCharacter() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        assertEquals("a", todoPage.get1stItemText());
    }
    @Test
    void newItemSingleWord() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        assertEquals("hello", todoPage.get1stItemText());
    }
// li:nth-child(2) label
    @Test
    void newItemSpecialCharacter() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("è");
        assertEquals("è", todoPage.get1stItemText());
    }
    @Test
    void newItemSymbol() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("@");
        assertEquals("@", todoPage.get1stItemText());
    }
    @Test
    void newItemPunctuation() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("!");
        assertEquals("!", todoPage.get1stItemText());
    }
    @Test
    void newItemSentence() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("We are testing the page");
        assertEquals("We are testing the page", todoPage.get1stItemText());
    }
    @Test
    void newItemCurrency() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("¥");
        assertEquals("¥", todoPage.get1stItemText());
    }
    @Test
    void modifyAnItem() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoBox = driver.findElement(By.cssSelector(".view > label"));
        act.doubleClick(todoBox).sendKeys("goodbye");
        Thread.sleep(3000);
        todoBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }


    // li:nth-child(2) label

    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}


