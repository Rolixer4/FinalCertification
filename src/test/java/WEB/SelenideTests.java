package WEB;

import WEB.DOM.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static Properties.GetProperties.getProperty;

@DisplayName("Тесты сайта saucedemo.com")
public class SelenideTests {

    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CompletePage completePage;
    OverviewPage overviewPage;


    @BeforeAll
    public static void setUpGlobal() throws IOException {
        Configuration.baseUrl = getProperty("URL");
        Configuration.headless = true;
    }

    @BeforeEach
    public void setUp() {
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        completePage = new CompletePage();
        overviewPage = new OverviewPage();
    }

    @Test
    @DisplayName("Авторизация валидного юзера")
    @Tag("positive")
    public void validUserLogin() {
        loginPage.login(getProperty("STANDARD_USERNAME"), getProperty("PASSWORD"));
        loginPage.clickLogin();
        productsPage.actuallyProductsPage();
    }

    @Test
    @DisplayName("Авторизация заблокированного юзера")
    @Tag("negative")
    public void blockedUserLogin() {
        loginPage.login(getProperty("LOCKED_USERNAME"), getProperty("PASSWORD"));
        loginPage.clickLogin();
        loginPage.checkError();
    }

    @Test
    @DisplayName("Сквозной сценарий пользователя standart_user")
    @Tag("positive")
    public void e2eStandardUser() {
        loginPage.login(getProperty("STANDARD_USERNAME"), getProperty("PASSWORD"));
        loginPage.clickLogin();
        productsPage.addToCart();
        productsPage.header.clickToCart();
        cartPage.itemsInCartShouldBe(3);
        cartPage.checkout();
        checkoutPage.insertDataForm(getProperty("FIRST_NAME"), getProperty("LAST_NAME"), getProperty("POSTAL_CODE"));
        checkoutPage.clickContinue();
        overviewPage.checkItemsInOverview();
        overviewPage.checkTotalPrice();
        overviewPage.finishOrder();
        completePage.checkComplete();
    }

    @Test
    @DisplayName("Сквозной сценарий пользователя performance_glitch_user")
    @Tag("positive")
    public void e2ePerformanceGlitchUser() {
        loginPage.login(getProperty("PERFORMANCE_GLITCH_USERNAME"), getProperty("PASSWORD"));
        loginPage.clickLogin();
        productsPage.addToCart();
        productsPage.header.clickToCart();
        cartPage.itemsInCartShouldBe(3);
        cartPage.checkout();
        checkoutPage.insertDataForm(getProperty("FIRST_NAME"), getProperty("LAST_NAME"), getProperty("POSTAL_CODE"));
        checkoutPage.clickContinue();
        overviewPage.checkItemsInOverview();
        overviewPage.checkTotalPrice();
        overviewPage.finishOrder();
        completePage.checkComplete();
    }
}
