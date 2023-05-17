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
        WebElement todoBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        Thread.sleep(2000);
        act.doubleClick(todoBox).perform();
        Thread.sleep(2000);
        WebElement todoBox2 = driver.findElement(By.className("edit"));
        todoBox2.sendKeys("goodbye");
        Thread.sleep(2000);
        todoBox2.sendKeys(Keys.ENTER);
        assertEquals("hellogoodbye", todoPage.get1stItemText());
    }
    @Test
    void modifyThenEscapeItem() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        Thread.sleep(2000);
        act.doubleClick(todoBox).perform();
        Thread.sleep(2000);
        WebElement todoBox2 = driver.findElement(By.className("edit"));
        todoBox2.sendKeys("goodbye");
        Thread.sleep(2000);
        todoBox2.sendKeys(Keys.ESCAPE);
        assertEquals("hello", todoPage.get1stItemText());
    }
    @Test
    void canCompleteItem() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        assertTrue(completeButton.isSelected());
    }
    @Test
    void canUncompleteItem() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        completeButton.click();
        assertFalse(completeButton.isSelected());
    }
    @Test
    void deleteIncompleteItem() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        Thread.sleep(2000);
        WebElement itemBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox).click().build().perform();
        Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton).click().build().perform();
//        act.click(deleteButton);
        Thread.sleep(2000);
//        assertTrue(todoPage.get1stItemText().isEmpty());
    }

///html/body/section/div/section/ul/li/div/button

    // li:nth-child(2) label
//    @Test
//    void newItemCurrency() throws InterruptedException {
//        ToDoPage todoPage = new ToDoPage(driver);
//        todoPage.navigateTo();
//        todoPage.addItem("¥");
//        assertEquals("¥", todoPage.get1stItemText());
//    }
    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}


