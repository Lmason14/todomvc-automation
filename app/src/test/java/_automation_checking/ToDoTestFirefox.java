package _automation_checking;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoTestFirefox {
    private static FirefoxDriver driver;

    @BeforeEach
    void launchBrowser() {
        driver = new FirefoxDriver();
    }
    @Test
    void newItemSingleCharacter()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        assertEquals("a", todoPage.get1stItemText());
    }
    @Test
    void newItemSingleWord()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        assertEquals("hello", todoPage.get1stItemText());
    }
// li:nth-child(2) label
    @Test
    void newItemSpecialCharacter()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("è");
        assertEquals("è", todoPage.get1stItemText());
    }
    @Test
    void newItemSymbol()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("@");
        assertEquals("@", todoPage.get1stItemText());
    }
    @Test
    void newItemPunctuation()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("!");
        assertEquals("!", todoPage.get1stItemText());
    }
    @Test
    void newItemSentence()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("We are testing the page");
        assertEquals("We are testing the page", todoPage.get1stItemText());
    }
    @Test
    void newItemCurrency()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("¥");
        assertEquals("¥", todoPage.get1stItemText());
    }
    @Test
    void modifyAnItem()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.doubleClick(todoBox).perform();
        WebElement todoBox2 = driver.findElement(By.className("edit"));
        todoBox2.sendKeys("goodbye");
        todoBox2.sendKeys(Keys.ENTER);
        assertEquals("hellogoodbye", todoPage.get1stItemText());
    }
    @Test
    void modifyAnItemUsingBackspace()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.doubleClick(todoBox).perform();
        WebElement todoBox2 = driver.findElement(By.className("edit"));
        todoBox2.sendKeys(Keys.BACK_SPACE);
        todoBox2.sendKeys(Keys.BACK_SPACE);
        todoBox2.sendKeys(Keys.BACK_SPACE);
        todoBox2.sendKeys(Keys.BACK_SPACE);
        todoBox2.sendKeys("ow are you");
        todoBox2.sendKeys(Keys.ENTER);
        assertEquals("how are you", todoPage.get1stItemText());
    }
    @Test
    void modifyThenEscapeItem()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.doubleClick(todoBox).perform();
        WebElement todoBox2 = driver.findElement(By.className("edit"));
        todoBox2.sendKeys("goodbye");
        todoBox2.sendKeys(Keys.ESCAPE);
        assertEquals("hello", todoPage.get1stItemText());
    }
    @Test
    void canCompleteItem()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        assertTrue(completeButton.isSelected());
    }
    @Test
    void canUncompleteItem()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        completeButton.click();
        assertFalse(completeButton.isSelected());
    }
    @Test
    void deleteIncompleteItem()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement itemBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox).click().build().perform();
        WebElement deleteButton = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton).click().build().perform();
        List<WebElement> deletedItem = driver.findElements(By.cssSelector(".view > label:nth-child(2)"));
        assertTrue(deletedItem.isEmpty());
    }

    @Test
    void deleteCompleteItem()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        WebElement itemBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox).perform();
        WebElement deleteButton = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton).click().build().perform();
        List<WebElement> deletedItem = driver.findElements(By.cssSelector(".view > label:nth-child(2)"));
        assertTrue(deletedItem.isEmpty());
    }


    @Test
    void statusBarDisplays0ItemsLeft()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("0 items left", todoText);
    }
    @Test
    void statusBarDisplaysOneItemsLeft()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("1 item left", todoText);
    }

    @Test
    void statusBarDisplaysTenItemLeft()  {
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
    void statusBarNotVisableWhenNoItemsLeftAfterDeleting()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement itemBox = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox).perform();
        WebElement deleteButton = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton).click().build().perform();
        List<WebElement> footerBar = driver.findElements(By.className("footer"));
        assertTrue(footerBar.isEmpty());
    }

    @Test
    void canToggleToActiveTasks()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        WebElement activeTab = driver.findElement(By.cssSelector(".filters > li:nth-child(3) > a:nth-child(1)"));
        activeTab.click();
        String pageURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/active", pageURL);
    }

    @Test
    void canToggleToAllCompleteTasks()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        WebElement completeTab = driver.findElement(By.cssSelector(".filters > li:nth-child(5) > a:nth-child(1)"));
        completeTab.click();
        String pageURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/completed", pageURL);
    }

    @Test
    void canToggleToAllTasks()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        WebElement completeTab = driver.findElement(By.cssSelector(".filters > li:nth-child(5) > a:nth-child(1)"));
        completeTab.click();
        WebElement allTab = driver.findElement(By.cssSelector(".filters > li:nth-child(1) > a:nth-child(1)"));
        allTab.click();
        String pageURL = driver.getCurrentUrl();
        assertEquals("https://todomvc.com/examples/react/#/", pageURL);
    }

    @Test
    void canClickClearCompleted()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        WebElement clearCompleteTab = driver.findElement(By.className("clear-completed"));
        clearCompleteTab.click();
        List<WebElement> clear = driver.findElements(By.className("clear-completed"));
        assertTrue(clear.isEmpty());
    }

    @Test
    void canClickClearCompletedAndCompletesItems()  {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        todoPage.taskNumberToggle(1);
        todoPage.taskNumberToggle(2).click();
        WebElement clearCompleteTab = driver.findElement(By.className("clear-completed"));
        clearCompleteTab.click();
        List<WebElement> itemCompleted = driver.findElements(By.cssSelector(".todo-list > li:nth-child(2) > div:nth-child(1) > input:nth-child(1)"));
        assertTrue(itemCompleted.isEmpty());
    }
    @Test
    void canCompleteAllWithDownArrow()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        WebElement completeAll = driver.findElement(By.cssSelector(".main > label:nth-child(2)"));
        completeAll.click();
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("0 items left", todoText);
    }
    @Test
    void canUnCompleteAllWithDownArrow()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        todoPage.addItem("goodbye");
        WebElement completeAll = driver.findElement(By.cssSelector(".main > label:nth-child(2)"));
        completeAll.click();
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        completeAll.click();
        String todoText = todoCount.getText();
        assertEquals("2 items left", todoText);
    }
    @Test
    void bigRunThrough() throws InterruptedException {
        Actions act = new Actions(driver);
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("Item 1");
        todoPage.addItem("Item 2");
        todoPage.addItem("Item 3");
        WebElement number3 = driver.findElement(By.cssSelector(".todo-list > li:nth-child(3) > div:nth-child(1) > label:nth-child(2)"));
        Thread.sleep(2000);
        WebElement itemBox = driver.findElement(By.cssSelector(".todo-list > li:nth-child(2) > div:nth-child(1) > label:nth-child(2)"));
        act.moveToElement(itemBox).click().build().perform();
        Thread.sleep(2000);
        WebElement deleteButton = driver.findElement(By.cssSelector(".todo-list > li:nth-child(2) > div:nth-child(1) > button:nth-child(3)"));
        act.moveToElement(deleteButton).click().build().perform();
        Thread.sleep(2000);
        todoPage.taskNumberToggle(1).click();
        Thread.sleep(2000);
        List<WebElement> noNumber3 = driver.findElements(By.cssSelector(".todo-list > li:nth-child(3) > div:nth-child(1) > label:nth-child(2)"));
        assertTrue(noNumber3.isEmpty());
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("1 item left", todoText);
        WebElement clearCompleteTab = driver.findElement(By.className("clear-completed"));
        clearCompleteTab.click();
        List<WebElement> itemNumber2 = driver.findElements(By.cssSelector(".todo-list > li:nth-child(2) > div:nth-child(1) > label:nth-child(2)"));
        assertTrue(itemNumber2.isEmpty());
        WebElement itemBox2 = driver.findElement(By.cssSelector(".view > label:nth-child(2)"));
        act.moveToElement(itemBox2).build().perform();
        Thread.sleep(2000);
        WebElement deleteButton1 = driver.findElement(By.cssSelector(".destroy"));
        act.moveToElement(deleteButton1).click().build().perform();
        Thread.sleep(2000);
        List<WebElement> footerBar = driver.findElements(By.className("footer"));
        assertTrue(footerBar.isEmpty());

    }
// assertEquals("line-through", defaultTodo.getCssValue("text-decoration-line"));
    @AfterEach
    void closeBrowser() {
        driver.quit();
    }
}


