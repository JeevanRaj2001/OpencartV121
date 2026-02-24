package testCases;
/*Data is valid - login success - test pass - logout
Data is valid -- login failed - test fail
Data is invalid - login success - test fail - logout
Data is invalid - login failed - test pass
*/


import org.testng.Assert;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;
import org.testng.annotations.Test;

public class TC003_LoginDDT extends BaseClass{
    @Test(dataProvider="LoginData", dataProviderClass= DataProviders.class, groups="Datadriven")//getting data provider from different class
    public void verify_loginDDT(String email, String pwd, String exp)
    {
        logger.info("*******Starting TC__003__LoginDDT*******");
        try {

            //HomePage
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clicklogin();

            //Login
            LoginPage lp = new LoginPage();
            lp.setEmail();
            lp.setPassword();
            lp.clickLogin();

            //MyAccount
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            if (exp.equalsIgnoreCase("valid")) {
                if (targetPage == true) {
                    mcc.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            }
            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    mcc.clickLogout();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);

                }
            }
        } catch(Exception e)
        {
            Assert.fail();
        }
        logger.info("*******finished TC__003__LoginDDT*******");

    }



}
