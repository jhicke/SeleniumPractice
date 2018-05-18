package odcStartup;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import odcStartup.ExcelFunction;
import odcStartup.PageModelFactory;


public class StartUpTest {
 
	private static final Logger log = LogManager.getLogger(StartUpTest.class);
	
	//pageModel Factory for elements 
	PageModelFactory page;

	WebDriver driver;
	String baseUrl = "https://www.odc4.com/";
	static String exePath = "C:\\Users\\Jonathon_Hicke\\Work Folders\\Documents\\Selenium\\chromedriver.exe";

	//create constants class to hid login
	String companyName = odcStartup.Constants.COMPANYNAME;
	String userName = odcStartup.Constants.USERNAME;
	String password = odcStartup.Constants.PASSWORD;

	//class object to handle loading in test data from excel.
	ExcelFunction testFile1 = new ExcelFunction();
	
	//set up chromedriver
	@BeforeSuite
	public void beforeSuite(){
		  System.setProperty("webdriver.chrome.driver", exePath);
			
		  driver = new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 //init the pagemodelfactory class
		  page = new PageModelFactory(driver);
		  
	}
	
	@AfterSuite
	public void afterSuite() throws InterruptedException {
		Thread.sleep(5000);
	//	driver.quit();
	}
	
	//test login and getting to exception page
	@Test(priority = 0)
  public void login() {
		log.info("Login");
		baseUrl = baseUrl+"Login.aspx";
		driver.get((baseUrl));
		
		/* removed to add in a Page Model Factory framework
		WebElement companyElmt = driver.findElement(By.id("txtCompany"));
		WebElement userElmt = driver.findElement(By.id("txtUserName"));
		WebElement passwordElmt = driver.findElement(By.id("txtPassword"));
		WebElement element;
		element = driver.findElement(By.id("imgLogin"));
		
		companyElmt.sendKeys(companyName);
		userElmt.sendKeys(userName);
		passwordElmt.sendKeys(password);	
		element.click();
		
		*/
		
		page.setCompanyName(companyName);
		page.setUserName(userName);
		page.setPassword(password);
		page.clickLoginBtn();
		
	
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue((currentUrl.equals("https://www.odc4.com/APDashboard.aspx") || currentUrl.equals("https://www.odc4.com/Search.aspx")), "failed Login");
		log.debug("Login successFul");
		
  }
	
	//test that search fields match requirement
	@Test(priority=1)
	public void search() throws Exception {
		log.info("Search screen QA start-");
		SoftAssert sa = new SoftAssert();
		ListIterator<WebElement> litr = null;
		String tempString;
		
		//load in excel file with expected values
		testFile1.loadExcelFile("C:\\Users\\Jonathon_Hicke\\Work Folders\\Documents\\Selenium\\excel\\exception.xlsx");
	
		List<String> testData = testFile1.getTestData("Sheet1");
		
		log.info("Data from excel loaded");
		
		
		//select exception tab
		WebElement element = driver.findElement(By.id("ctl00_Odclnkbtn_Exceptions_426"));
		element.click();
		
		//get each search field xpath for getting just the labels from the search table.
		//html//div[@id='ctl00_ContentPlaceHolder1_pnlBasicSearch']//tr/td/span[@class='DynamicLabel_text']
		 List<WebElement> searchList = driver.findElements(By.xpath("//html//div[@id='ctl00_ContentPlaceHolder1_pnlBasicSearch']//tr/td/span[@class='DynamicLabel_text']"));
		
		 
		 
		// correct list is in the testfile and web site is in searchlist
		log.info("Check to see if test list and web match");
		 assertEquals(searchList.size(), testData.size());
		 log.info("lists match size");
		 log.info("comparing values");
		
		 litr= searchList.listIterator();
		 for(String line : testData) {
			 tempString =  litr.next().getText().trim();
			 log.trace("expected -"+ line + " actual -" + tempString);
			sa.assertEquals(line,tempString);
			 
		 }
		 sa.assertAll();
	}
	
	//Testing the load of the excel file
	@Test(enabled=false)
	public void excelTest() {
		try {
			testFile1.loadExcelFile("C:\\Users\\Jonathon_Hicke\\Work Folders\\Documents\\Selenium\\excel\\exception.xlsx");
			testFile1.getTestData("Sheet1");
			log.info("Data from excel loaded");
		} catch (Exception e) {
			System.out.println("excelLoad failed");
		}
		
		
	}
	
	
	
}
