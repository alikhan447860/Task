package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.LoginPage;
import utilities.OTPReader;

public class LoginTest extends BaseTest {

    @Test(description = "Verify Complete Login Flow")
    public void verifyLoginNavigation() throws Exception {

        LOG.info("===== Starting Complete Login Flow =====");

        LoginPage loginPage = new LoginPage();

        String email = ConfigReader.getInstance().getProperty("gmail.email");

        loginPage
                .acceptCookiesIfPresent()
                .clickProfileIcon()
                .clickLoginButton()
                .clickContinueWithEmail()
                .enterEmail(email)
                .clickContinueButton();

        Thread.sleep(3000);

        String otp = OTPReader.getLatestOTP();

        LOG.info("OTP Retrieved Successfully: {}", otp);

        loginPage
                .enterOTP(otp)
                .clickSignIn();

        LOG.info("===== Login Completed Successfully =====");
    }
}