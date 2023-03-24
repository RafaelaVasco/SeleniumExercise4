package PedidosYa.pageobjects;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage extends PageObject {
    @FindBy(xpath = "//*[@id = 'location__search__form']")
    WebElementFacade address_button;

    @FindBy(xpath = "//*[@id = 'search_address_input']")
    WebElementFacade searchAddress_input;

    @FindBy(xpath = "//button[@data-testid = 'location-v2-map-footer-confirm']")
    WebElementFacade confirm_button;

    @FindBy(xpath = "//input[@name = 'complement']")
    WebElementFacade department_input;

    @FindBy(xpath = "//button[@type = 'submit']")
    WebElementFacade confirmAddress_button;

    @FindBy(xpath = "//a[text() = 'Ver más...']")
    WebElementFacade verMas_link;

    @FindBy(xpath = "//a[text() = 'Mejor puntuados']")
    WebElementFacade bestRated_link;

    @FindBy(xpath = "//h1")
    WebElementFacade restaurantName_label;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[1]/div/div[1]/div/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/span[1]")
    WebElementFacade deliveryDelay_label;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div[1]/div/div[1]/div/div[2]/div/span")
    WebElementFacade ratingValue;

    @FindBy(xpath = "//a[contains(@href, '/info')]")
    WebElementFacade restaurantInfo_link;

    @FindBy(xpath = "//*[@id=\"fixed_header\"]/div/svg")
    WebElementFacade closeModal_button;

    @FindBy(xpath = "//div[contains(./div[4], 'Horarios')]/div[4]/div")
    List<WebElementFacade> schedules;

    @FindBy(xpath = "//a[contains(@href, 'opiniones')]")
    WebElementFacade reviews_button;

    static String xpathRestaurants = "//div[@id = 'main-content']/div[4]/a";
    static List<WebElementFacade> myListOfRestaurants;
    static List<WebElementFacade> myReviewsOfRestaurant;

    public void setAddress() {
        address_button.click();
        WebElementFacade modal_address = withTimeoutOf(10, TimeUnit.SECONDS).find(By.xpath("//div[@id = 'modal-manager-component']"));
        searchAddress_input.sendKeys("Constituyente 1792, Montevideo, Uruguay");

        WebElementFacade firstAddressResult = withTimeoutOf(5, TimeUnit.SECONDS).find(By.xpath("//*[@id = 'location_autocomplete_0']"));
        firstAddressResult.click();

        WebElementFacade modal_map = withTimeoutOf(10, TimeUnit.SECONDS).find(By.xpath("//div[@id = 'modal-manager-component']"));
        confirm_button.click();

        WebElementFacade modal_information = withTimeoutOf(10, TimeUnit.SECONDS).find(By.xpath("//div[@id = 'modal-manager-component']"));
        department_input.sendKeys("512");
        confirmAddress_button.click();
    }

    public void searchRestaurantByType(String type) {
        verMas_link.click();

        WebElementFacade filterElement = find(By.xpath("//div[@role = 'button' and contains(.//div, '" + type +"')]"));
        filterElement.click();

        myListOfRestaurants = withTimeoutOf(20, TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurants));

        Serenity.recordReportData().withTitle("Total amount of results").andContents("The number of results on the page is " + myListOfRestaurants.size());
    }

    public void selectFilter(String filter) {
        WebElementFacade filterElement = find(By.xpath("//a[text() = '" + filter + "']"));
        filterElement.click();

        myListOfRestaurants = withTimeoutOf(20, TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurants));

        Serenity.recordReportData().withTitle("Total amount of results filtered").andContents("The number of results on the page after applying filter is " + myListOfRestaurants.size());
    }

    public void sortByHighestScore() {
        bestRated_link.click();

        myListOfRestaurants = withTimeoutOf(20, TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurants));
        String score_old = myListOfRestaurants.get(0).find(By.xpath("//div[@id = 'shop_card_result']/div/div[3]//span")).getText();
        for(Integer i = 1; i < myListOfRestaurants.size(); i++) {
            String score_new = myListOfRestaurants.get(i).find(By.xpath("//div[@id = 'shop_card_result']/div/div[3]//span")).getText();
            Assert.assertTrue(Double.parseDouble(score_old) >= Double.parseDouble(score_new));
            score_old = score_new;
        }
    }

    public void selectFirstResult() {
        myListOfRestaurants.get(0).click();
    }

    public void getDetails() {
        String restaurantName = restaurantName_label.getText();
        String score = "The restaurant score is " + ratingValue.getText();
        String deliveryDelay = "The restaurant takes between " + deliveryDelay_label.getText() + " to deliver the order";

        restaurantInfo_link.click();
        String schedule = "";
        for (WebElementFacade element : schedules) {
            schedule += element.getText() + "\n";
        }
        closeModal_button.click();

        reviews_button.click();
        myReviewsOfRestaurant = withTimeoutOf(10, TimeUnit.SECONDS).findAll(By.xpath("//*[@id = 'modal__content']/div/div/div/div/div/div[2]"));
        String firstReview = "The oldest review is " + myReviewsOfRestaurant.get(0).getText();
        String secondReview = "The second oldest review is " + myReviewsOfRestaurant.get(1).getText();
        String thirdReview = "The third oldest review is " + myReviewsOfRestaurant.get(2).getText();

        String details = restaurantName + "\n" + score + "\n" + deliveryDelay + "\n" + schedule + "\n\n" + firstReview + "\n\n" + secondReview + "\n\n" + thirdReview;

        Serenity.recordReportData().withTitle("Details and Reviews").andContents(details);
    }

}
