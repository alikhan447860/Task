package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.LoginPage;
import pages.PaymentPage;
import pages.ShopPage;
import utilities.OTPReader;

public class PurchaseTest extends BaseTest {

    @Test(description = "Verify Complete Purchase Flow")
    public void verifyPurchaseFlow() throws Exception {

        LOG.info("===== Starting Purchase Flow =====");

        String email = ConfigReader.getInstance().getProperty("gmail.email");

        LoginPage loginPage = new LoginPage();

        loginPage
                .acceptCookiesIfPresent()
                .clickProfileIcon()
                .clickLoginButton()
                .clickContinueWithEmail()
                .enterEmail(email)
                .clickContinueButton();

        Thread.sleep(10000);

        String otp = OTPReader.getLatestOTP();

        LOG.info("OTP Retrieved Successfully : {}", otp);

        loginPage
                .enterOTP(otp)
                .clickSignIn();

        LOG.info("Login Successful");

        ShopPage shopPage = new ShopPage();

        shopPage
        .closeWelcomePopup()
        .clickProfileIcon()
        .clickShop()
        .validateAndPrintCategories()
        .clickVault()
        .selectFirstProduct();

String selectedPrice = shopPage.getSelectedProductPrice();

LOG.info("Selected Product Price : {}", selectedPrice);

shopPage.waitForPaymentPage();

        LOG.info("Selected Product Price : {}", selectedPrice);

        LOG.info("===== Purchase Flow Completed Successfully =====");
    }
}