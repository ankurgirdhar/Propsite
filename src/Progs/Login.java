package Progs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
	String URL = "https://www.proptiger.com";
	// a comments
	@BeforeClass
		public void init()
		{
			System.setProperty("webdriver.chrome.driver","chromedriver");
			driver = new ChromeDriver();
			wait = new WebDriverWait(driver,10);
		}

		//@Test
		public void gSignIn()
		{
			driver.manage().deleteAllCookies();
			driver.get(URL);
			//wait for sign in text to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='topMenuLinkBtn']")));
			driver.findElement(By.cssSelector("div[class='topMenuLinkBtn']")).click();
			//wait.until(ExpectedConditions.)
			
			//wait for Google sign in button to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='btn-google']")));
			driver.findElement(By.cssSelector("button[class='btn-google']")).click();
			try{
				Thread.sleep(3000);}catch(InterruptedException e){System.out.println("Thread exception");}
			
			
			/*Switch to new window*/ 
			driver.switchTo().activeElement();	//
			//driver.switchTo().window("Sign in - Google Accounts");
			driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
			System.out.println(driver.getTitle());
			
			
			//send value for email field in google window
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
			driver.findElement(By.id("Email")).sendKeys("dummyproptiger@gmail.com");
			driver.findElement(By.id("next")).click();
			
			//send value for password field in google window
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id='Passwd']")));
			driver.findElement(By.id("Passwd")).sendKeys("dummyuse");
			driver.findElement(By.id("signIn")).click();
			
			
			try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println("Thread exception");}	
			driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
			validateLogin();
		}
		
		
		//@Test
		public void fbSignIn()
		{
			driver.manage().deleteAllCookies();
			System.out.println(driver.getWindowHandle().toString());
			driver.navigate().refresh();
			
			//wait for sign in text to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='topMenuLinkBtn']")));
			driver.findElement(By.cssSelector("div[class='topMenuLinkBtn']")).click();
			
			
			//wait for Google sign in button to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='btn-facebook marginT10']")));
			driver.findElement(By.cssSelector("button[class='btn-facebook marginT10']")).click();
			try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println("Thread exception");}
			
			
			/*Switch to new window*/ 
			driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
			System.out.println(driver.getTitle());
			
			
			//send value for email field in google window
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
			driver.findElement(By.id("email")).sendKeys("proptiger46@gmail.com");
			driver.findElement(By.id("pass")).sendKeys("proptiger@46");
			driver.findElement(By.id("u_0_2")).click();
			
			
			try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println("Thread exception");}	
			driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
			validateLogin();
		}

		
//		SSHUser=sysadmin
//				SSHHost=54.255.59.216
//				SSHLocalPort=3312
//				SSHRemoteHost=127.0.0.1
//				SSHRemotePort=3306

		
	@Test	
	public void signup()
	{
		driver.manage().deleteAllCookies();
		driver.get(URL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='topMenuLinkBtn']")));
		driver.findElement(By.cssSelector("div[class='topMenuLinkBtn']")).click();
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Signup']")));
		driver.findElement(By.xpath("//button[text()='Signup']")).click();
		try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println("Thread exception");}
		
		
		//Fill the form for register
		driver.findElement(By.xpath("//input[@data-ng-model='uname']")).sendKeys("qa-auto");
		driver.findElement(By.xpath("//input[@data-ng-model='eid']")).sendKeys("qa@qa.qa");
		driver.findElement(By.xpath("//input[@data-ng-model='pwd']")).sendKeys("123456");
		
		Select country  = new Select(driver.findElement(By.xpath("//select[@data-ng-model='country']")));
		driver.findElement(By.xpath("//select[@data-ng-model='country']")).click();
		country.selectByValue("0");
		driver.findElement(By.xpath("//input[@data-ng-model='phn']")).sendKeys("1326412965");
		driver.findElement(By.xpath("//button[text()='Signup']")).click();
		
	}
	
	public void invalidUserSignIn()
	{
		
	}
		
		
	public void validateLogin()
	{
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='dropdown-toggle topMenuLinkBtn ng-binding']")).getText()=="DUMMY");
	}
	

	
	
	@Test
	public void login()
	{
		WebDriver d = new FirefoxDriver();
		//d.get("https://www.proptiger.com");
		d.get("https://www.proptiger.com");
		d.findElement(By.xpath("//span[text()='Sign In']")).click();
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//existing user sign in
		d.findElement(By.xpath("//input[@id='login-id']")).sendKeys("ankur.girdhar@proptiger.com");
		d.findElement(By.xpath("//input[@id='login-pass']")).sendKeys("demo@proptiger");
		d.findElement(By.xpath("//button[text()='Sign In']")).click();
		d.findElement(By.xpath("//span[text()='Account']")).click();
		if (d.findElement(By.xpath("//ul[@class='dropdown-menu']/li/span[text()='xc.kvj']")).isDisplayed())
		{
			System.out.println("User logged in by normal username and password, test case paxssed");
		}
	}
	
	
	//@AfterClass
		public void closeWindow()
		{
			driver.close();
			driver.quit();
		}
	
	
}
