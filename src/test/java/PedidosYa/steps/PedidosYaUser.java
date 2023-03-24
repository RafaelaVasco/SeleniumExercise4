package PedidosYa.steps;

import PedidosYa.pageobjects.MainPage;
import net.serenitybdd.core.steps.ScenarioActor;
import net.thucydides.core.annotations.Steps;

public class PedidosYaUser extends ScenarioActor {
    String actor;

    @Steps(shared = true)
    MainPage mainPage;

    public void navigateTo() {
        mainPage.setDefaultBaseUrl("https://www.pedidosya.com.uy/");
        mainPage.open();
    }

    public void setAddress() throws InterruptedException {
        mainPage.setAddress();
    }

    public void searchRestaurantByFoodType(String type) {
        mainPage.searchRestaurantByType(type);
    }

    public void filterBy(String filter) {
        mainPage.selectFilter(filter);
    }

    public void sortByScore() {
        mainPage.sortByHighestScore();
    }

    public void enterFirstResult() {
        mainPage.selectFirstResult();
    }

    public void viewDetails() {
        mainPage.getDetails();
    }
}
