package resources;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class MyUtilities {

    /**
     * Alternative click to be applied which is highly recommended for form pages and/or elements that could be out of view scope
     * @param element Specify element to click on
     * @param driver Specify webdriver instance
     */
    public static void moveNclick (WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    public static int randomNumber (int minArg, int maxArg){
        int min = minArg;
        int max = maxArg;
        return new Random().nextInt(max - min + 1) + min;
    }

}
