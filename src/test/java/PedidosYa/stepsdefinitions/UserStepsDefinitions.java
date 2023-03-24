package PedidosYa.stepsdefinitions;

import PedidosYa.steps.PedidosYaUser;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

public class UserStepsDefinitions {
    @Steps(shared = true)
    PedidosYaUser pedidosYaUser;

    @Given("^user navigates to https://www.pedidosya.com.uy/$")
    public void userNavigateToPedidosYa() {
        pedidosYaUser.navigateTo();
        Serenity.takeScreenshot();
    }

    @And("^set the address$")
    public void setAddress() throws InterruptedException {
        pedidosYaUser.setAddress();
        Serenity.takeScreenshot();
    }

    @Given("^user search restaurant (.*) and report the amount of results on the page$")
    public void searchRestaurantByFoodType(String type) {
        pedidosYaUser.searchRestaurantByFoodType(type);
        Serenity.takeScreenshot();
    }

    @When("^filter by (.*) and report the amount of results on the page$")
    public void filterBy(String filter) {
        pedidosYaUser.filterBy(filter);
        Serenity.takeScreenshot();
    }

    @And("^order the results by 'Mejor puntuados' and validate it$")
    public void sortByScore() {
        pedidosYaUser.sortByScore();
        Serenity.takeScreenshot();
    }

    @And("^select the first result$")
    public void userSelectFirstResult() {
        pedidosYaUser.enterFirstResult();
        Serenity.takeScreenshot();
    }

    @Then("^User views restaurant important information$")
    public void userViewDetails() {
        pedidosYaUser.viewDetails();
        Serenity.takeScreenshot();
    }
}
