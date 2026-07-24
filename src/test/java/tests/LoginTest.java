package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.OTPReader;

public class LoginTest extends BaseTest {

    @Test(description = "Verify Complete Login Flow")
    public void verifyLoginNavigation() throws Exception {

        LOG.info("===== Starting Complete Login Flow =====");

        LoginPage loginPage = new LoginPage();

        loginPage
                .acceptCookiesIfPresent()
                .clickProfileIcon()
                .clickLoginButton()
                .clickContinueWithEmail()
                .enterEmail("khanali6068@gmail.com")
                .clickContinueButton();

        // Wait for OTP email to arrive
        Thread.sleep(3000);

        // Read OTP from Gmail
        String otp = OTPReader.getLatestOTP();

        LOG.info("OTP Retrieved Successfully: {}", otp);

        // Enter OTP and Sign In
        loginPage
                .enterOTP(otp)
                .clickSignIn();

        LOG.info("===== Login Completed Successfully =====");
    }
}