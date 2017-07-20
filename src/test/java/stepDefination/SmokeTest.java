package stepDefination;

import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import libraries.ReportLibrary;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by 50021824 on 19-07-2017.
 */
public class SmokeTest
{
    WebDriver driver;

    String xpathAccountProfile = "//*[@id='acctmenu']//a[contains(text(),'Account Profile')]";
    String xpathUsername2 = "//input[@name='userName']";
    String xpathUpdateButton2 = "(.//button[@name='btnUpdate'])[1]";
    String xpathSuccess = "//div[@class='message_tag']//div";
    String xpathLogout = "//a[contains(@href,'accountLogout')]";


    @Given("^Open Chrome and start application using \"([^\"]*)\"$")
    public void open_Chrome_and_start_application_using(String arg1)  {
       try {
           ReportLibrary.Get_Report_Library_Instance();
           ReportLibrary.startReport("Gherkin001", "Gherkin001" );
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Gherkin001 Execution started" , LogStatus.INFO, false);
           System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivers\\chromedriver.exe");
           DesiredCapabilities capablities = DesiredCapabilities.chrome();
           capablities.setCapability("applicationCacheEnabled", true);
           ChromeOptions options = new ChromeOptions();
           options.addArguments("--start-maximized");
           options.addArguments("--enable-application-cache");
           //String chromeProfilePath = CommonLibrary.settingsSheetInfo.get("CHROME_PROFILE");
           //options.addArguments("user-data-dir="+chromeProfilePath);
           driver = new ChromeDriver( options);
           driver.manage().window().maximize();
           driver.get(arg1);
       }
       catch (Exception e)
       {
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
       }



    }

    @When("^I enter valid \"([^\"]*)\" and valid \"([^\"]*)\"$")
    public void i_enter_valid_and_valid(String arg1, String arg2)  {
        try{


            String xpathUserName = "//input[@id='tt_username1']";
            String xpathPassword = "//input[@id='tt_loginPassword1']";
            driver.findElement(By.xpath(xpathUserName)).sendKeys(arg1);
            driver.findElement(By.xpath(xpathPassword)).sendKeys(arg2);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login Details Entered" , LogStatus.PASS, true);
        }
       catch (Exception e)
       {
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }
    }

    @Then("^Click on login buttton user should able to login successfully$")
    public void click_on_login_buttton_user_should_able_to_login_successfully()  {
      try{
        String xpathLoginButton = "//button[@name='btnLogin']";

        driver.findElement(By.xpath(xpathLoginButton)).click();
      }
      catch (Exception e)
      {
          ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
      }
    }

    @Given("^user is on web account maintenance home page$")
    public void user_is_on_web_account_maintenance_home_page()  {
        try{
        String xpathLoginHome = "//h4[contains(text(),'ACCOUNT')]";
        WebDriverWait wait2 = new WebDriverWait(driver, 50);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLoginHome)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Home page is loaded" , LogStatus.INFO, false);
        }
        catch (Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }

    }

    @When("^profile button is visible$")
    public void profile_button_is_visible()  {
        try {

            WebDriverWait wait2 = new WebDriverWait(driver, 50);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAccountProfile)));
        }
          catch (Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }
    }

    @Then("^Click on profile button$")
    public void click_on_profile_button()  {
        try {
        driver.findElement(By.xpath(xpathAccountProfile)).click();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Clicked on Account profile" , LogStatus.INFO, false);
        }
        catch (Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }
    }

    @Given("^User is on profile page$")
    public void user_is_on_profile_page()  {
        try{
        WebDriverWait wait2 = new WebDriverWait(driver, 50);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathUsername2)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Profile page is loaded  " , LogStatus.INFO, false);
        }
        catch (Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }
    }

    @When("^username input box appears$")
    public void username_input_box_appears()  {
        try{
        driver.findElement(By.xpath(xpathUsername2)).clear();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "User input box appeared " , LogStatus.INFO, false);
        }
        catch (Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }
    }

    @Then("^Update the \"([^\"]*)\"$")
    public void update_the(String arg1)  {
        try{
        String abc=arg1+ DateTime.now().getMillisOfSecond();
        driver.findElement(By.xpath(xpathUsername2)).sendKeys(abc);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changing the username " + abc, LogStatus.INFO, false);

        driver.findElement(By.xpath(xpathUpdateButton2)).click();
        String op = driver.findElement(By.xpath(xpathSuccess)).getText();
        System.out.println(op);
        if (op.contains("Account Details Updated Successfully")) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changed the username  to: " + abc + " successfully", LogStatus.PASS, true);
        } else {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changed the username  to: " + abc + " successfully", LogStatus.FAIL, true);
        }

        JavascriptExecutor je = (JavascriptExecutor) driver;

        WebElement element = driver.findElement(By.id(xpathLogout));

        je.executeScript("arguments[0].scrollIntoView(true);", element);
        je.executeScript("window.scrollBy(0,-150)", "");
        Thread.sleep(1000);
        WebDriverWait wait2 = new WebDriverWait(driver, 50);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLogout)));
        driver.findElement(By.xpath(xpathLogout)).click();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Application logout successfully", LogStatus.INFO, false);

        ReportLibrary.endTest();
        ReportLibrary.endReport();
        }
        catch (Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed Due to"+e.getMessage() , LogStatus.INFO, false);
        }


        finally {
            ReportLibrary.endTest();
            ReportLibrary.endReport();
        }
    }




}
