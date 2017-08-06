package Test;

import Page.GooglePage;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class IteaTests {
    public WebDriver webDriver;
    public String searchKeys = "ITEA";

    /**
     * Initialises FirefoxDriver and navigates to Google page
     */
    @Parameters({"BrowserType"})
    @BeforeClass
    public void beforeClass(@Optional("firefox") String BrowserType) {
        FirefoxDriverManager.getInstance().setup();
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("https://www.google.com.ua/");
    }

    /**
     * Kills WebDriver instance
     */
    @AfterClass
    public void afterClass() {
        webDriver.quit();
    }

    @Test
    public void testSearchResults() throws InterruptedException {
     String searchText = "ITEA";
     GooglePage googlePage = new GooglePage(webDriver);
     googlePage.searchResults(searchKeys);

     Assert.assertTrue(googlePage.isPageLoaded(), "Search results page is not loaded");
     Assert.assertEquals(googlePage.getSearchResultsCount(), 7, "Results count doesn't match real");
     Assert.assertTrue(googlePage.isAllResultContains(searchText), "Not every result contains search term");

     googlePage.switchToSecondSearchPage();
     Assert.assertTrue(googlePage.isPageLoaded(), "Search results page is not loaded");
     Assert.assertEquals(googlePage.getSearchResultsCount(), 10, "Results count doesn't match real");
     Assert.assertTrue(googlePage.isAllResultContains(searchText), "Not every result contains search term");
    }
}
