package _automation_checking;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class ToDoPage {
    protected WebDriver driver;

    public ToDoPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get("https://todomvc.com/examples/react/#/");
    }

    public void addItem(String itemItem) {
        WebElement todoBox = driver.findElement(By.cssSelector(".new-todo"));
        todoBox.sendKeys(itemItem);
        todoBox.sendKeys(Keys.ENTER);
    }

    public String get1stItemText() {
        return driver.findElement(By.cssSelector(".view > label")).getText();
    }

    public void modifyItem(String modItem) {
        Actions act = new Actions(driver);
        WebElement todoBox = driver.findElement(By.id(".0.1.2.$3fcb6e35-f669-45f8-a7e5-cda75348cf55.0.1"));
        act.doubleClick(todoBox).perform();
        todoBox.sendKeys(modItem);
        todoBox.sendKeys(Keys.ENTER);
    }
}
