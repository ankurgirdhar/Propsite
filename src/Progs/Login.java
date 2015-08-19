package Progs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Login 
{
	WebDriverWait wait;
	WebDriver driver;
	WebElement ele ;
	
	String URL = "https://beta.proptiger-ws.com";
	By signintext = By.xpath("//div[@data-ng-if='!loggedIn'][@data-ng-click='openSignIn()']/div[1]");
	By loggedintext = By.xpath("//div[@data-ng-if='loggedIn']/div[1]");
	By nonloggedintext = By.xpath("//div[@data-ng-if='!loggedIn']/div[1]");
	
	//For Google Sign in 
	By gloginbutton = By.cssSelector("button[class='btn-google']");
	By gemail= By.id("Email");
	By gnext =By.id("next");
	By gpassword = By.id("Passwd");
	By gsignin = By.id("signIn");
	
	//For FB Sign In
	By fbloginbutton = By.cssSelector("button[class='btn-facebook marginT10']");
	By fbemail = By.id("email");
	By fbpassword = By.id("pass");
	By fbsignin = By.id("u_0_2");
	
	//For Sign Up
	By signupbutton = By.xpath("//button[text()='Signup']");
	By uname = By.xpath("//input[@data-ng-model='uname']");
	By uemail = By.xpath("//input[@data-ng-model='eid']");
	By upassword = By.xpath("//input[@data-ng-model='pwd']");
	By ucountry = By.xpath("//select[@data-ng-model='country']");
	By uphone = By.xpath("//input[@data-ng-model='phn']");
	By errormessage = By.cssSelector("span[data-ng-if='regErrEmail']");
	//By signupbutton = By.xpath("//button[text()='Signup']");
	
	//For existing user login
	By usersignin = By.xpath("//span[text()='Sign In']");
	By loginid = By.xpath("//input[@id='login-id']");
	By loginpassword = By.xpath("//input[@id='login-pass']");
	By signinbutton = By.xpath("//button[text()='Sign In']");
	
	@BeforeClass
	public void init()
	{
		System.setProperty("webdriver.chrome.driver","chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver,10);	
	}
	
	public WebElement waitnfind(By element)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		return driver.findElement(element);
	}

	@Test
	public void gSignIn()
	{
		driver.manage().deleteAllCookies();
		driver.get(URL);
		ele = waitnfind(signintext);
		ele.click();
		
		//wait for Google sign in button to appear
		ele = waitnfind(gloginbutton);
		ele.click();
		try{Thread.sleep(3000);}catch(InterruptedException e){e.printStackTrace();}
		
		/*Switch to new window*/ 
		driver.switchTo().activeElement();	//
		//driver.switchTo().window("Sign in - Google Accounts");
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		System.out.println(driver.getTitle());
		
		
		//send value for email field in google window
		ele = waitnfind(gemail);
		ele.sendKeys("dummyproptiger@gmail.com");
		driver.findElement(gnext).click();
		
		//send value for password field in google window
		ele = waitnfind(gpassword);
		ele.sendKeys("dummyuse");
		driver.findElement(gsignin).click();
		
		try{Thread.sleep(5000);}catch(InterruptedException e){e.printStackTrace();}	
		driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
		int i = validateLogin();
		System.out.println("value of i is " + i);
		Assert.assertTrue(i==1);
	}
				
	@Test
	public void fbSignIn()
	{
		driver.manage().deleteAllCookies();
		System.out.println(driver.getWindowHandle().toString());
		//driver.navigate().refresh();
		driver.get("https://beta.proptiger-ws.com");
		
		//wait for sign in text to appear
		ele = waitnfind(signintext);
		ele.click();
					
		//wait for Google sign in button to appear
		ele = waitnfind(fbloginbutton);
		ele.click();
		try{Thread.sleep(3000);}catch(InterruptedException e){e.printStackTrace();}
		
		/*Switch to new window*/ 
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		System.out.println(driver.getTitle());
		
		//send value for email field in fb window
		ele=waitnfind(fbemail);
		ele.sendKeys("proptiger46@gmail.com");
		driver.findElement(fbpassword).sendKeys("proptiger@46");
		driver.findElement(fbsignin).click();
		
		try{Thread.sleep(8000);}catch(InterruptedException e){e.printStackTrace();}	
		driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
		int i = validateLogin();
		System.out.println("value of i is " + i);
		Assert.assertEquals(i,1);
	}
		
	@Test	
	public void signup()
	{
		driver.manage().deleteAllCookies();
		driver.get(URL);
		ele = waitnfind(signintext);
		ele.click();
	
		ele = waitnfind(signupbutton);
		ele.click();
		try{Thread.sleep(3000);}catch(InterruptedException e){e.printStackTrace();}
				
		//Fill the form for register
		driver.findElement(uname).sendKeys("qa-auto");
		
		driver.findElement(upassword).sendKeys("123456");
		
		Select country  = new Select(driver.findElement(ucountry));
		country.selectByValue("0");
		driver.findElement(uphone).sendKeys("1326412965");
		
		Integer n=3;
		
		do{
		n++;	
		String emailid="qab+".concat(n.toString()).concat("@qa.qa");
		driver.findElement(uemail).clear();
		driver.findElement(uemail).sendKeys(emailid);
		
		
		driver.findElement(signupbutton).click();	
		}while(driver.findElements(errormessage).size()==1);
		
		System.out.println("pch gya k");
		
		try{Thread.sleep(3000);}catch(InterruptedException e){e.printStackTrace();}	
		
		int i = validateLogin();
		System.out.println("value of i  is " + i);
		Assert.assertEquals(i, 1);
	}
	
	public void invalidUserSignIn()
	{
		
	}
	
	@Test
	public void login()
	{
		driver.manage().deleteAllCookies();
		driver.get("https://beta.proptiger-ws.com");
			
		//wait = new WebDriverWait(d,10);
		ele = waitnfind(signintext);
		ele.click();
		
		//existing user sign in
		ele=waitnfind(loginid);
		ele.sendKeys("qa@qa.qa");
		driver.findElement(loginpassword).sendKeys("qaqaqa");
		driver.findElement(signinbutton).click();
		try{Thread.sleep(5000);}catch(InterruptedException e){e.printStackTrace();}	

		int i = validateLogin();
		System.out.println("value of i  is " + i);
		Assert.assertEquals(i, 1);
	}
	
	public int validateLogin()		//returns 1 for logged in user and 0 for non logged in
	{
		if (driver.findElements(loggedintext).size()==0 && driver.findElements(nonloggedintext).size()>0){
			System.out.println(driver.findElements(nonloggedintext).get(0).getText());
			return 0;
		}
		else if ((driver.findElements(loggedintext).size()==1 && driver.findElements(nonloggedintext).size()==0))
		{	
			System.out.println(driver.findElement(loggedintext).getText());
			return 1;
		}
		else
			System.out.println("problem is there, can't decide whether user is logged in or not");
			return 2;
	}
	
	//@AfterClass
	public void closeWindow()
	{
		driver.close();
		driver.quit();
	}
	
	
}
