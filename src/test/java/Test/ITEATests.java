package Test;

import Page.GooglePage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ITEATests {
    public WebDriver webDriver;
    public String searchKeys = "ITEA";

    @Parameters({"BrowserType"})

    @BeforeMethod
    public void beforeMethod(@Optional("firefox") String BrowserType) throws InterruptedException {
        if (BrowserType.toLowerCase().equals("chrome")) {
            ChromeDriverManager.getInstance().setup();
            webDriver = new ChromeDriver();
        }
        if (BrowserType.toLowerCase().equals("firefox")) {
            FirefoxDriverManager.getInstance().setup();
            webDriver = new FirefoxDriver();
        }
        webDriver.navigate().to("https://www.google.com.ua/");
    }

    /**
     * Kills WebDriver instance
     */
    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

@Test
    public void testSearchResults() throws InterruptedException {
     GooglePage googlePage = new GooglePage(webDriver);
     googlePage.searchResults(searchKeys);
     Assert.assertTrue(googlePage.isPageLoaded(), "Search results page is not loaded");
     int resultsCount = googlePage.getSearchResultsCount();
     Assert.assertEquals(resultsCount, 10, "Results count doesn't match real");

     googlePage.switchToSecondSearchPage();
     Assert.assertEquals(resultsCount, 10, "Results count doesn't match real");

    }
}
