package testCases;


import org.testng.Assert;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import org.testng.annotations.Test;

public class TC002_LoginTest extends BaseClass{
    @Test(groups={"Sanity", "Master"})
    public void verify_login()
    {
        logger.info("*****Starting TC_002_LoginTest****");
        try {
            //HomePage
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clicklogin();

            //Login
            LoginPage lp = new LoginPage();
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clickLogin();

            //MyAccount
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            //Assert.assertEquals(targetPage, "Login failed");
            Assert.assertTrue(targetPage);
        }
        catch (Exception e)
        {
            Assert.fail();
        }
        logger.info("******Finished TC_002_LoginTest******");



    }

}
