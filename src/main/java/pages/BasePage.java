package pages;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

public class BasePage {
    public WebDriver driver;

    public WebDriver initializeDriver (String browserType) {
        switch(browserType){
            case "mozilla":
                initializeMozilla();
                break;
            case "chrome":
                initializeChrome();
                break;
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private void initializeChrome (){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\chromedriver_win.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
    }

    private void initializeMozilla (){
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\resources\\geckodriver_win.exe");

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        options.addPreference("dom.webnotifications.enabled", false);

        driver = new FirefoxDriver(options);
    }

    public void getScreenshot(String result) throws IOException
    {
        //In future occasions may be use
        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("C://test//"+result+"screenshot.png")); //to be decided location to save screenshots in case is done
    }

}
