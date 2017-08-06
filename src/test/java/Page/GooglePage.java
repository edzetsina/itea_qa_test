package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Google Page
 */
public class GooglePage {
    /**
     * WebDriver instance
     *
     * @param webDriver WebDriver instance
     */
    public GooglePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public WebDriver webDriver;

    /**
     * Identify search field
     */
    @FindBy(id = "lst-ib")
    private WebElement searchField;
    /**
     * Identify Search button element
     */
    @FindBy(name = "btnK")
    private WebElement searchButton;
    /**
     * Identify search results elements
     */
    @FindBy(xpath = "//*[@class='rc']")
    private WebElement searchResults;
    /**
     * Identify list of elements search results
     */
    @FindBy(xpath = ".//*[@id='res']//*[@class='g']")
    private List<WebElement> searchResultsList;
    /**
     * Identify second result page
     */
    @FindBy(xpath = ".//*[@id='nav']//td[3]")
    private WebElement secondSearchPage;

    /**
     * @param searchKeys String search text
     * @return Google Page
     */
    public GooglePage searchResults(String searchKeys) {
        searchField.sendKeys(searchKeys);
        searchButton.click();
        return PageFactory.initElements(webDriver, GooglePage.class);
    }

    public boolean isPageLoaded() {
        return searchResults.isDisplayed();
    }

    /**
     * Gets count of search results at the page
     *
     * @return int quantity of results
     */
    public int getSearchResultsCount() {
        return searchResultsList.size();
    }

    /**
     * Method which open second search results page
     */
    public GooglePage switchToSecondSearchPage() {
        secondSearchPage.click();
        return this;
    }

    /**
     * Gets List of desired search results details elements
     *
     * @param searchText String "searchHeaders", "searchAddresses"
     * @return listDetails List of WebElements
     */
    public List<String> getSearchDetails(String searchText) {
        List<String> listDetails = new ArrayList<>();
        String elementXpath = getSearchDetailsXpath(searchText);
        for (WebElement resultElement : searchResultsList) {
            String searchDetailsText = resultElement.findElement(By.xpath(elementXpath)).getText();
            listDetails.add(searchDetailsText);
        }
        return listDetails;
    }

    /**
     * gets Xpath string to get search details
     *
     * @param searchText String "searchHeaders", "searchAddresses"
     * @return String with xpath to get search details elements
     */
    public String getSearchDetailsXpath(String searchText) {

        switch (searchText.toLowerCase()) {
            case "searchheaders":
                return ".//h3//a";
            case "searchaddresses":
                return ".//cite";
            default:
                return "";
        }
    }

    /**
     * Checks if all of results on the page contains searched keywords
     *
     * @param searchText String keywords
     * @return true if all result contains search keywords
     */
    public boolean isAllResultContains(String searchText) {
        List<String> listHeaders = getSearchDetails("searchHeaders");
        List<String> listAddresses = getSearchDetails("searchAddresses");
        boolean result = false;
        for (int i = 0; i < searchResultsList.size(); i++) {
            boolean isAddressElementContainsSearchTerm = listAddresses.get(i).toLowerCase().contains(searchText.toLowerCase());
            boolean isHeaderElementContainsSearchTerm = listHeaders.get(i).toLowerCase().contains(searchText.toLowerCase());
            result = isAddressElementContainsSearchTerm || isHeaderElementContainsSearchTerm;
        }
        return result;
    }
}
