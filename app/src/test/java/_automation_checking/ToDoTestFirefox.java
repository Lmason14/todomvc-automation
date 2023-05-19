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

    // When we add a single character it is listed as a todo item
    @Test
    void newItemSingleCharacter()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        assertEquals("a", todoPage.get1stItemText());
    }

    // When we add a single word string it is listed as a todo item
    @Test
    void newItemSingleWord()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        assertEquals("hello", todoPage.get1stItemText());
    }

    // When we add a single letter string from a language other than english it is listed as a todo item
    @Test
    void newItemSpecialCharacter()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("è");
        assertEquals("è", todoPage.get1stItemText());
    }

    // When we add a single symbol it is listed as a todo item
    @Test
    void newItemSymbol()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("@");
        assertEquals("@", todoPage.get1stItemText());
    }

    // When we add a a single punctuation mark it is listed as a todo item
    @Test
    void newItemPunctuation()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("!");
        assertEquals("!", todoPage.get1stItemText());
    }

    // When we add a string sentence it is listed as a todo item
    @Test
    void newItemSentence()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("We are testing the page");
        assertEquals("We are testing the page", todoPage.get1stItemText());
    }

    // When we add a single currency denotation string it is listed as a todo item
    @Test
    void newItemCurrency()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("¥");
        assertEquals("¥", todoPage.get1stItemText());
    }

    // When we add a new todo item and when it becomes listed we double click on it, then can add to the string and then this changes the item
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

    // When we add a new todo item and when it becomes listed we double click on it, we then use backspace and then add more text, which then changes the item
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

    // When we add a new todo item and when it becomes listed we double click on it, then when we add more text but press "escape" these changes do not remain
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

    // When we add a new todo item and when we click the button next to it, it becomes completed
    @Test
    void canCompleteItem()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("a");
        WebElement completeButton = driver.findElement(By.cssSelector(".toggle"));
        completeButton.click();
        assertTrue(completeButton.isSelected());
    }

    // When we add a new todo item and when we click the button next to it, it becomes completed and clicking again uncompletes it
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

    // When we add a new todo item and when we click the cross button next to it, it removes it from the list
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

    // When we add a new todo item, then click complete and then when we click the cross button next to it, it removes it from the list
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

    // When we add a new todoItem and then complete it, we can see the status "0 items left" in the status bar
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
    // When we add a new todoItem, we can see the message "1 item left" in the status bar at the bottom of the list
    @Test
    void statusBarDisplaysOneItemsLeft()  {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        todoPage.addItem("hello");
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        String todoText = todoCount.getText();
        assertEquals("1 item left", todoText);
    }
    //When we add 10 todoItem's, we can see the message "10 items left" in the status bar at the bottom of the list
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
    // When we initially load the page up we can see that there is no status bar as no items have been added
    @Test
    void statusBarNotVisableOnLoadUpOfPage() {
        ToDoPage todoPage = new ToDoPage(driver);
        todoPage.navigateTo();
        List<WebElement> footerBar = driver.findElements(By.className("footer"));
        assertTrue(footerBar.isEmpty());
    }
        // When we add an item and then delete it, we can verify that the status bar disappears
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
    // After adding 2 items and completing 1, we can click the active tab and view items which have not been completed
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
    // After adding 2 items and completing 1, we can click the completed tab and view items which have been completed
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
    // After adding 2 items and completing 1, we can click the completed tab and click the all tab to view both active and completed items
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
    // After adding 2 items, we can complete one and then click the clear completed button, and only see 1 active item left
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
    // After adding 2 items, we can complete one and then click the clear completed button, and only see 1 active item left
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
        // After adding 2 items, we can complete them all by clicking the down arrow
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
    // After adding 2 items, we can complete them all by clicking the down arrow, clicking it again will then uncomplete the items
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
    // One test to cover quite a few actions, adding items, completing and removing items and then finishing with an empty list.
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


