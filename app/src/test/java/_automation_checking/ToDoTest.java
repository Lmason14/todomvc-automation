package _automation_checking;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest {
    private static FirefoxDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Test
    void shouldGetURL() {
        driver.get("https://todomvc.com/examples/react/#/");
        assertEquals("React • TodoMVC",driver.getTitle());
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
}
