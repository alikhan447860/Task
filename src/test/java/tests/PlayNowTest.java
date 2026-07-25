package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.LoginPage;
import utilities.OTPReader;

public class PlayNowTest extends BaseTest {

    @Test(description = "Verify Play Now Navigation")
    public void verifyPlayNowNavigation() throws Exception {

        LOG.info("===== Starting Play Now Test =====");

        String email = ConfigReader.getInstance().getProperty("gmail.email");

        LoginPage loginPage = new LoginPage();

        loginPage
                .acceptCookiesIfPresent()
                .clickProfileIcon()
                .clickLoginButton()
                .clickContinueWithEmail()
                .enterEmail(email)
                .clickContinueButton()
                .verifyCheckYourInboxHeading();

        Thread.sleep(10000);

        String otp = OTPReader.getLatestOTP();

        LOG.info("OTP Retrieved Successfully : {}", otp);

        loginPage
                .enterOTP(otp)
                .clickSignIn()
                .clickPlayNow()
                .switchToPlayWindow()
                .clickPlayImage();

        LOG.info("===== Play Now Test Completed Successfully =====");
    }
}