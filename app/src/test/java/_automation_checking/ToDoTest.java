package _automation_checking;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.List;
import java.util.function.BooleanSupplier;

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
        Thread.sleep(2000);
        List<WebElement> deletedItem = driver.findElements(By.cssSelector(".view > label:nth-child(2)"));
        assertTrue(deletedItem.isEmpty());
    }

    @Test
    void deleteCompleteItem() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        Thread.sleep(2000);
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        WebElement itemBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox).perform();
        Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton).click().build().perform();
        Thread.sleep(2000);
        List<WebElement> deletedItem = driver.findElements(By.cssSelector(".view > label:nth-child(2)"));
        assertTrue(deletedItem.isEmpty());
    }


    @Test
    void statusBarDisplays0ItemsLeft() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        Thread.sleep(2000);
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("0 items left", todoText);
    }
    @Test
    void statusBarDisplaysOneItemsLeft() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        Thread.sleep(2000);
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("1 item left", todoText);
    }

    @Test
    void statusBarDisplaysTenItemLeft() throws InterruptedException {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("1");
        todoPage.addItem("2");
        todoPage.addItem("3");
        todoPage.addItem("4");
        todoPage.addItem("5");
        todoPage.addItem("6");
        todoPage.addItem("7");
        todoPage.addItem("8");
        todoPage.addItem("9");
        todoPage.addItem("10");
        Thread.sleep(2000);
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("10 items left", todoText);
    }

    @Test
    void statusBarNotVisableOnLoadUpOfPage() {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        List<WebElement> footerBar = driver.findElements(By.className("footer"));
        assertTrue(footerBar.isEmpty());
    }
    @Test
    void statusBarNotVisableWhenNoItemsLeftAfterDeleting() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        Thread.sleep(2000);
        WebElement itemBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox).perform();
        Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton).click().build().perform();
        Thread.sleep(2000);
        List<WebElement> footerBar = driver.findElements(By.className("footer"));
        assertTrue(footerBar.isEmpty());
    }

    @Test
    void canToggleToActiveTasks() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        Thread.sleep(2000);
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        Thread.sleep(2000);
        WebElement activeTab = driver.findElement(By.cssSelector(".filters > li:nth-child(3) > a:nth-child(1)"));
        activeTab.click();
        String pageURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/active", pageURL);
    }

    @Test
    void canToggleToAllCompleteTasks() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        Thread.sleep(2000);
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        Thread.sleep(2000);
        WebElement completeTab = driver.findElement(By.cssSelector(".filters > li:nth-child(5) > a:nth-child(1)"));
        completeTab.click();
        String pageURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/completed", pageURL);
    }

    @Test
    void canToggleToAllTasks() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        Thread.sleep(2000);
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        Thread.sleep(2000);
        WebElement completeTab = driver.findElement(By.cssSelector(".filters > li:nth-child(5) > a:nth-child(1)"));
        completeTab.click();
        Thread.sleep(2000);
        WebElement allTab = driver.findElement(By.cssSelector(".filters > li:nth-child(1) > a:nth-child(1)"));
        allTab.click();
        Thread.sleep(2000);
        String pageURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/", pageURL);
    }

    @Test
    void canClickClearCompleted() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        Thread.sleep(2000);
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        Thread.sleep(2000);
        WebElement clearCompleteTab = driver.findElement(By.className("clear-completed"));
        clearCompleteTab.click();
        List<WebElement> clear = driver.findElements(By.className("clear-completed"));
        assertTrue(clear.isEmpty());
    }

    @Test
    void canClickClearCompletedAndCompletesItems() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        Thread.sleep(2000);
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        Thread.sleep(2000);
        WebElement clearCompleteTab = driver.findElement(By.className("clear-completed"));
        clearCompleteTab.click();
        List<WebElement> itemCompleted = driver.findElements(By.cssSelector(".todo-list > li:nth-child(2) > div:nth-child(1) > input:nth-child(1)"));
        assertTrue(itemCompleted.isEmpty());
    }


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


