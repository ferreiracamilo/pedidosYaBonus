package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.MyUtilities;

import java.util.List;

public class SearchPage {

    /**
     * Region Variables
     */
    private WebDriver driver;
    private WebDriverWait  wait;

    private static final String xpInputSearchFood = "//div[@id='search_container'] //input[@type='search' and @id='search_input']";

    private static final String xpBusinessGetQTYByElements = "//div[@id='shop_card_result']"; //maximum as default to display in first page is 50
    private static final String xpBusinessNameByIndex = "(//div[@id='shop_card_result']) [$Index] //div[1] //div[2] //div[1] //div";
    private static final String xpBusinessRatingByIndex = "(//div[@id='shop_card_result']) [$Index] //*[name()='svg'] //parent::div //span";
    private static final String xpBusinessAnchorByIndex = "(//div[@id='shop_card_result']) [$Index] //div[1] //div[2] //div[1] //div //ancestor::a";

    private static final String xpFiltersContainsChannelFilterGetQTY = "//div[contains (@id,'channel_filter_') ]"; //Use to randomized then the index from xpFilterByIndex
    private static final String xpFilterChannelByIndex = "//div[@id='channel_filter_$Index'] //a"; //Starting from zero

    private static final String xpBtnSeeMoreResults = "//button[contains (text(),'Ver más resultados') ]";
    private static final String xpQtyResultsH3 = "//h3[contains(text(),'rest')]"; //PeYa lets you know results found under this element

    @FindBy(xpath = xpInputSearchFood)
    private WebElement inputSearchFood;

    @FindBy(xpath = xpBusinessGetQTYByElements)
    private List<WebElement> businessFoundList;

    @FindBy(xpath = xpFiltersContainsChannelFilterGetQTY)
    private List<WebElement> filterChannelFoundList;

    @FindBy(xpath = xpBtnSeeMoreResults)
    private WebElement buttonMoreResults;

    @FindBy(xpath = xpQtyResultsH3)
    private WebElement textQtyResultsFound;

    /**
     * Region Constructor
     */
    public SearchPage (WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);
        PageFactory.initElements(driver, this);
    }

    /**
     * Region Methods
     */

    /**
     * Before applying filter is suggested to input food you want to search and submit the search with writeFoodBoxAndSubmit
     * @param index Specify the index of element you want to click
     */
    public void clickFilterChannelByIndex(int index){
        WebElement filterChannelSelected = driver.findElement(By.xpath(xpFilterChannelByIndex.replace("$Index",Integer.toString(index))));
        MyUtilities.moveNclick(filterChannelSelected,driver);
    }

    /**
     * Write the searchbox to look for specific food and submit the search
     * @param food Specify food to search for
     */
    public void writeFoodBoxAndSubmit (String food){
        inputSearchFood.sendKeys(food);
        inputSearchFood.submit();
    }

    /**
     * Get quantity of displayed restaurants in view
     * @return int - qty
     */
    public int countRestaurants (){
        return businessFoundList.size();
    }

    /**
     * Get quantity of displayed channel filters
     * @return int - qty
     */
    public int countFilterChannels (){
        return filterChannelFoundList.size();
    }

    /**
     * Click button to get more restaurants displayed in view
     */
    public void clickMoreResultsButton (){
        MyUtilities.moveNclick(buttonMoreResults,driver);
    }

    /**
     * Given the phrase of "XXX restaurantes" extract digits to return in form of number
     * <mark>Example if phrase is "432 restaurantes" -> return would be 432</mark>
     * @return int - qty
     */
    public int getResultsGivenByPeYa (){
        String shrink = textQtyResultsFound.getText();
        shrink = shrink.substring(0,shrink.indexOf(" ")); //On this way we'll get only the numbers from "98 resultados encontrados" as example
        int qtyResults = Integer.getInteger(shrink); //transform text into int and return it
        return qtyResults;
    }

    /**
     * Get a specific restaurant name by index
     * <mark>Before you must check how many elements are in view scope</mark>
     * @param index
     * @return String
     */
    public String getRestaurantNameByIndex(int index){
        return driver.findElement(By.xpath(xpBusinessNameByIndex.replace("$Index",Integer.toString(index)))).getText();
    }

    /**
     * Get a specific restaurant star rating by index, stars take into consideration are the full ones
     * <mark>Before you must check how many elements are in view scope</mark>
     * @param index
     * @return int
     */
    public int getRestaurantRatingByIndex(int index){
        return driver.findElements(By.xpath(xpBusinessRatingByIndex.replace("$Index",Integer.toString(index)))).size();
    }

    /**
     * Click on specific restaurant to retrieve more detailed information
     * <mark>Before you must check how many elements are in view scope</mark>
     * @param index
     */
    public void clickRestaurantByIndex(int index){
        WebElement restaurantSelected = driver.findElement(By.xpath(xpBusinessAnchorByIndex.replace("$Index",Integer.toString(index))));
        MyUtilities.moveNclick(restaurantSelected,driver);
    }

}
