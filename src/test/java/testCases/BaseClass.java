package testCases;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Parameters;


public class BaseClass {
    public static WebDriver driver;
    public Logger logger;//Log4j
    public Properties p;

    @BeforeClass(groups={"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws FileNotFoundException
    {
        //Loading config.properties file
        FileReader file = new FileReader("./src//main//resources//config.properties");
        p = new Properties();
        p.load();




        logger = LogManager.getLogger(this.getClass());//log4j2

        if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            //os
            if(os.equalsIgnoreCase("windows"))
            {
                capabilities.setPlatform(Platform.WIN11);

            }
            else if(os.equalsIgnoreCase("mac"))
            {
                capabilities.setPlatform(Platform.MAC);

            }
            else
            {
                System.out.println("No matching os");
                return;
            }

            //browser
            switch(br.toLowerCase())
            {
                case "chrome": capabilities.setBrowserName("chrome"); break;
                case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
                case "firefox": capabilities.setBrowserName("fireofx"); break;
                default: System.out.println("No matching browser"); return;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        }
        if(p.getProperty("execution_env").equalsIgnoreCase("local"))
        {
            switch(br.toLowerCase())
            {
                case "chrome": driver = new ChromeDriver(); break;
                case "edge": driver = new EdgeDriver(); break;
                case "firefox": driver = new FirefoxDriver(); break;
                default : System.out.println("Invalid browser name.."); return;


            }

        }



        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("appURL"));//reading url from properties file
        driver.manage().window().maximize();

    }
    @AfterClass(groups={"Sanity", "Regression", "Master"})
    public void teardown()
    {
        driver.quit();

    }
    public String randomeString()
    {
        String generatedstring = RandomStringUtils.randomAlphanumeric(6);
        return generatedstring;
    }

    public String randomeNumber()
    {
        String generatednumber = RandomStringUtils.randomNumeric(10);
        return generatednumber;
    }

    public String randomeAlphaNumeric()
    {
        String generatedstring = RandomStringUtils.randomAlphanumeric(3);
        String generatednumber = RandomStringUtils.randomNumeric(3);
        return (generatedstring+"#"+generatednumber);


    }
    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath = System.getProperty("user.dir")+"\\screnshots\\"+tname+"_"+timeStamp+".png";
        File targetFile = new File(targetFilePath);
        return targetFilePath;


    }
}
