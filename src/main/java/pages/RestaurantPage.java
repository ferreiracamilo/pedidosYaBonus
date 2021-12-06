package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RestaurantPage {

    /**
     * Region Variables
     */

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String xpButtonCloseModal = "//div[@id='fixed_header'] //*[name()='svg']"; //Button to close either reviews modal or reviews modal

    private static final String xpButtonInfo = "//header //*[name()='svg'] //ancestor::a[contains(@href,'info')]";
    private static final String xpGetGeneralInfoLines = "//div[@class='sc-4iizj3-0 fnKrge']"; //Retrieve qty of general info lines
    private static final String xpGetGeneralInfoLinByIndex = "(//div[@class='sc-4iizj3-0 fnKrge']) [$Index]";
    private static final String xpGetScheduleLines = "//div[@class='sc-aqbanh-2 fnXRfN']"; //Retrieve qty of schedule lines
    private static final String xpGetDaySchedByIndex = "(//div[@class='day_name']) [$Index]";
    private static final String xpGetHourSchedByIndex = "(//span[@class='shift first']) [$Index]";

    private static final String xpButtonReviews = "//div[@class='sc-1t04lwr-0 dFJVzO'] //button";
    private static final String xpQtyStarByReviewIndex = "(//div[@class='sc-1cr54o1-0 bJzLvt'] /div[1] /div[1] /div[1]) [$Index] //*[name()='svg' and @type='full']"; //Use find elements to get full start svg icon qty to retrieve rating

    @FindBy(xpath = xpButtonCloseModal)
    private WebElement buttonCloseModal;

    @FindBy(xpath = xpButtonInfo)
    private WebElement buttonInfo;

    @FindBy(xpath = xpButtonReviews)
    private WebElement buttonReviews;

    @FindBy(xpath = xpGetGeneralInfoLines)
    private List<WebElement> generalInfoLineList;

    @FindBy(xpath = xpGetScheduleLines)
    private List<WebElement> scheduleLineList;

    /**
     * Region Constructor
     */
    public RestaurantPage (WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
        PageFactory.initElements(driver, this);
    }

    /**
     * Region Methods
     */

    /**
     * Close either restaurant's reviews or info modal
     */
    public void closeModal (){
        buttonCloseModal.click();
    }

    /**
     * Open restaurant's info modal
     */
    public void openRestaurantInfo (){
        buttonInfo.click();
    }

    /**
     * Open restaurant's reviews modal
     */
    public void openRestaurantReviews (){
        buttonReviews.click();
    }

    /**
     * Count general info lines within info modal
     * @return int - qty
     */
    public int countGeneralInfoLines (){
        return generalInfoLineList.size();
    }

    /**
     * Count schedule lines within info modal
     * @return int - qty
     */
    public int countScheduleLines (){
        return scheduleLineList.size();
    }

    /**
     * Get a specific general info line
     * <mark>Before using this, you should check out how many general info lines you have with 'countGeneralInfoLines'</mark>
     * @param index
     * @return String
     */
    public String getGeneralInfoLineByIndex (int index){
        return driver.findElement(By.xpath(xpGetGeneralInfoLinByIndex.replace("Index",Integer.toString(index)))).getText();
    }

    /**
     * Get a specific general info line
     * <mark>Before using this, you should check out how many general info lines you have with 'countScheduleLines'</mark>
     * @param index
     * @return String
     */
    public String getScheduleLineByIndex (int index){
        return driver.findElement(By.xpath(xpGetDaySchedByIndex.replace("$Index",Integer.toString(index)))).getText() + " " + driver.findElement(By.xpath(xpGetHourSchedByIndex.replace("$Index",Integer.toString(index)))).getText();
    }

    /**
     * Get a specific general info line
     * <mark>For this case could apply also the checkout initially quantity of reviews, however most of restaurant at least have 3 or 5 reviews</mark>
     * @param index
     * @return int - qty
     */
    public int getStarRatingByIndex (int index){
        return driver.findElements(By.xpath(xpQtyStarByReviewIndex.replace("$Index",Integer.toString(index)))).size();
    }

}
