package tests;

import org.testng.annotations.Test;

import config.ConfigReader;
import utilities.OTPReader;

public class OTPReaderTest {

	    
	 
	    @Test
	    public void verifyOTPReader() throws Exception {

	        ConfigReader.getInstance().printAllProperties();

	        String email = ConfigReader.getInstance().getProperty("gmail.email");
	        String password = ConfigReader.getInstance().getProperty("gmail.app.password");

	        System.out.println("Email = " + email);
	        System.out.println("Password Length = " + password.length());

	        String otp = OTPReader.getLatestOTP();

	        System.out.println("OTP = " + otp);
	    }
}
