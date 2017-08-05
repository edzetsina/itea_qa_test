package Page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GooglePage {

    public WebDriver webDriver;

    public GooglePage(WebDriver webDriver) {
      PageFactory.initElements(webDriver, this);
    }


    @FindBy (xpath = "//input[@id='gs_htif0']")
    private WebElement searchField;
    @FindBy (xpath = "//*[@name='btnK']")
    private  WebElement searchButton;
    @FindBy (xpath = ".//*[@id='res']")
    private WebElement searchResults;
    @FindBy(xpath = ".//*[@id='res']//*[@class='g']")
    private List<WebElement> searchResultsList;
    @FindBy (xpath = ".//*[@id='nav']//td[3]")
    private WebElement secondSearchPage;

    public GooglePage searchResults(String searchKeys) {
        searchField.sendKeys(searchKeys);
        searchButton.click();
        return this;
    }
    public boolean isPageLoaded() {
        return searchResults.isDisplayed();
    }

    public int getSearchResultsCount() {
        return searchResultsList.size();
    }

    public GooglePage switchToSecondSearchPage() {
        secondSearchPage.click();
        return this;
    }
    public boolean isAllSearchResultsContainKeywords(String keyword) {
        for (WebElement iteaSearch : searchResultsList) {
            if (!iteaSearch.getText().equalsIgnoreCase(keyword)) {
                return false;
            }
        }
        return true;
    }
}
