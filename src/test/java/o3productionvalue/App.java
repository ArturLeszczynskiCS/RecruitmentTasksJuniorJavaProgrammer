package o3productionvalue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class App {

    final private static String CHROME_DRIVER_LOCATION = System.getProperty("user.dir") + "\\chromedriver.exe";

    public static void main(String[] args) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());

        try {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.okg.se/en");

            WebElement element = driver.findElement(By.className("gauge-container"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element);
            actions.perform();

            Thread.sleep(5000);

            String svgTextValue = driver.findElement(By.xpath
                            ("//*[name()='svg' and @class='the-gauge']/*[local-name()='text' and @class='value-text']"))
                    .getText();
            System.out.println("value:" + svgTextValue + ", time:" + formatter.format(date));

            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
