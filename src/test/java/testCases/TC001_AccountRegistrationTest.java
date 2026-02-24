package testCases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.AccountsRegistrationPage;
import pageObjects.HomePage;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.apache.commons.lang3.RandomStringUtils;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups={"Regression", "Master"})
    public void verify_account_registration()
    {
        try {
            logger.info("****Starting TC001_AccountRegistrationTest***** ");
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount Link");
            hp.clickRegister();
            logger.info("Clicked on Register Link");

            logger.info("Providing customer details...");
            AccountsRegistrationPage regpage = new AccountsRegistrationPage(driver);
            regpage.setFirstName(randomeString().toUpperCase());
            regpage.setLastName(randomeString().toUpperCase());
            regpage.setEmail(randomeString() + "@gmail.com");//randomly generate the email
            regpage.setTelphone(randomeNumber());

            String password = RandomStringUtils.randomAlphanumeric(10);
            String pwd = randomeAlphaNumeric();
            regpage.setPassword(pwd);
            regpage.setConfirmPassword(pwd);

            regpage.setPrivacyPolicy();
            regpage.clickContinue();

            logger.info("Validating expected message..");
            String confmsg = regpage.getConfirmationMsg();
            if(confmsg.equals("Your Account Has Been Created!"))
            {
                Assert.assertTrue(true);
            }
            else
            {
                logger.error("Test Failed...");
                logger.debug("Debug logs...");
                Assert.assertTrue(false);
            }

        }
        catch(Exception e)
        {

            Assert.fail();
        }
        logger.info("****Finished TC001_AccountRegistrationTest***** ");



    }


}
