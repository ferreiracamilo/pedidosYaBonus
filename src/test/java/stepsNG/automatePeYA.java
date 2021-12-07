package stepsNG;

import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;
import pages.BasePage;
import pages.MainPage;
import pages.RestaurantPage;
import pages.SearchPage;
import resources.MyUtilities;

import java.util.ArrayList;
import java.util.List;

public class automatePeYA extends BasePage {

    //Cannot check whole solution due to being identified as a robot within chrome and mozilla map won't load to confirm location

    /*
    ACTIONS TO BE DONE AFTER SOME MODIFICATION DUE TO SAVE EFFORT OR NOT BEING APPLICABLE ANYMORE, ALONG APPLYING TESTNG INSTEAD OF CUCUMBER
    Go to https://www.pedidosya.com.uy/
    Set any street
    Click Search button (Confirm the street, do not move the location)
    Search for “Pizza”
    Select a random Filter (Express, Sellos, etc)
    Report the star rating and name of first 5 restaurants
    Report total no. of Search results in the current page
    Verify the number of results is correct
    Go into the first result from the search results
    Log all critical information of the selected restaurant:
    Details (distance, open hours, etc)
    First 3 customer reviews (rating points)

    Bonus: Run them on firefox too
    Note: Finally was applied TestNG

    * */

    final static Logger logger = Logger.getLogger(automatePeYA.class);

    @BeforeMethod
    @Parameters({ "browser-name" })
    public void openBrowserAndGoToPeya (String browserName){
        //Change parameter within testng.xml for initilizing other browser
        initializeDriver(browserName);
        driver.get("https://www.pedidosya.com.uy/");
    }

    @AfterMethod
    public void tearDown (){
        driver.quit();
    }

    @DataProvider(name = "test-data-addresses")
    public Object[][] dataProvAddress(){
        return new Object[][]{
                {"Florida 1276"},{"Comandante Braga 2582"}
        };
    }

    @Test(dataProvider ="test-data-addresses",priority = 1)
    public void mainPageExecution (String address){
        MainPage mainP = new MainPage(driver);
        mainP.writeLocation(address);
        mainP.clickSearchButton();
        mainP.confirmLocation();
    }

    @Test(priority = 2, enabled = false)
    public void searchPageExecution (){
        SearchPage searchP = new SearchPage(driver);
        searchP.writeFoodBoxAndSubmit("Pizza");
        searchP.clickFilterChannelByIndex(MyUtilities.randomNumber(0,searchP.countFilterChannels()-1));
        for(int i=1; i<=5;i++){
            // fiveRest.add(searchP.getRestaurantNameByIndex(i) + " has a "+ searchP.getRestaurantRatingByIndex(i) + " star rating");
            logger.info(searchP.getRestaurantNameByIndex(i) + " has a "+ searchP.getRestaurantRatingByIndex(i) + " star rating");
        }
        logger.info("In the current page there's "+searchP.countRestaurants()+" restaurant/s");

        //Verify the number of results is correct
        do{
            searchP.clickMoreResultsButton();
        }while (searchP.getResultsGivenByPeYa() > searchP.countRestaurants());
        Assert.assertEquals(searchP.getResultsGivenByPeYa(),searchP.countRestaurants());

        searchP.clickRestaurantByIndex(1);
    }

    @Test(priority = 3, enabled = false)
    public void restaurantPageExecution (){
        RestaurantPage restaurantP = new RestaurantPage(driver);

        restaurantP.openRestaurantInfo();
        for (int i=1;i<=restaurantP.countGeneralInfoLines();i++){
            logger.info(restaurantP.getGeneralInfoLineByIndex(i));
        }

        for (int i=1;i<=restaurantP.countScheduleLines();i++){
            logger.info(restaurantP.getScheduleLineByIndex(i));
        }
        restaurantP.closeModal();

        restaurantP.openRestaurantReviews();
        //Will be record only stars, since some reviews did not actually contain description
        logger.info(restaurantP.getStarRatingByIndex(1));
        logger.info(restaurantP.getStarRatingByIndex(2));
        logger.info(restaurantP.getStarRatingByIndex(3));

    }

}
