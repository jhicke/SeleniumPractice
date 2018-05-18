package odcStartup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageModelFactory {

	WebDriver driver;
	
	@FindBy(id="imgLogin")
	WebElement loginPageLoginBtn;
	
	@FindBy(id="txtCompany")
	WebElement loginPageCompanyField;
	
	@FindBy(id="txtUserName")
	WebElement loginPageUserNameField;
	
	@FindBy(id="txtPassword")
	WebElement loginPagePasswordField;
	
	public PageModelFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setCompanyName(String name) {
		loginPageCompanyField.sendKeys(name);
	}
	
	public void setUserName(String name) {
		loginPageUserNameField.sendKeys(name);
	}
	
	public void setPassword(String name) {
		loginPagePasswordField.sendKeys(name);
	}
	
	public void clickLoginBtn() {
		loginPageLoginBtn.click();
	}
}
