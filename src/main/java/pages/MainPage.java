package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    /**
     * Region Variables
     */
    private WebDriver driver;
    private WebDriverWait  wait;

    //On previous project on the xpath was not implemented either static or final, however this will assure that xpath are not being modified unless is done manually by coding
    private static final String xpInputSearchbox = "//input[@id='search_address_input']";
    private static final String xpButtonSearchbox = "//button[@type='submit']";
    private static final String xpButtonConfirmLocation = "//button[@id='confirm_location_btn']";

    @FindBy(xpath = xpInputSearchbox)
    private WebElement inputSearchbox;

    @FindBy(xpath = xpButtonSearchbox)
    private WebElement buttonSearchbox;

    @FindBy(xpath = xpButtonConfirmLocation)
    private WebElement buttonConfirmLocation;

    /**
     * Region Constructor
     */
    public MainPage (WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
        PageFactory.initElements(driver, this);
    }

    /**
     * Region Methods
     */

    /**
     * Write location to order and receive food
     * @param location text to send to input
     */
    public void writeLocation (String location){
        inputSearchbox.sendKeys(location);
    }

    /**
     * After specifying the location click on search button, this will trigger a confirm location modal window
     */
    public void clickSearchButton (){
        buttonSearchbox.click();
    }

    /**
     * Confirm location by cliking on button
     */
    public void confirmLocation (){
        buttonConfirmLocation.click();
    }


}
