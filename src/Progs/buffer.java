package Progs;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//comment

public class buffer 
{	
	WebDriver d;
	Object[][] ob;
	FileInputStream fin;
	HSSFWorkbook wb;
	HSSFSheet sh;
	HSSFCell cell,cell2;
	HSSFRow row;
	
	/*public void getURLStatus()
	{
		WebDriver d = new FirefoxDriver();
		d.get("https://www.proptiger.com");
		List<WebElement> s = d.findElements(By.tagName("a"));
		for (WebElement e:s)
		{
			System.out.println(e.getAttribute("href") + "text is " + e.getText());
		}
	}*/
	//@Test
	public void buffer1()
	{
		//d=new FirefoxDriver();
		//d.get("https://www.proptiger.com");
		//d.findElement(By.xpath("//body")).sendKeys(Keys.CONTROL + "t");
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		d=new ChromeDriver();
		d.get("http://www.proptiger.com");
		try{
		Thread.sleep(5000);
		}catch(InterruptedException E){E.printStackTrace();}
		//get title
		String title = d.getTitle();
		System.out.println(title);
		WebElement e = d.findElement(By.cssSelector("meta[name='description']"));
		System.out.println(e.getAttribute("content"));
		e = d.findElement(By.cssSelector("meta[name='keywords']"));
		System.out.println(e.getAttribute("content"));
		System.out.println(d.findElement(By.tagName("H1")).getText());
		System.out.println(d.findElement(By.tagName("H2")).getText());
		
		List<WebElement> images= d.findElements(By.tagName("img"));
		for (WebElement f : images)
		{
			String alttext = (f.getAttribute("alt"));
			if (alttext.isEmpty())
			System.err.println("No alt text found for image " + f.getAttribute("src"));
			else
			System.out.println(alttext);
		}
		
		
		
		
		
		JavascriptExecutor jse = (JavascriptExecutor)d;
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		try{
			Thread.sleep(2000);
			}catch(InterruptedException E){E.printStackTrace();}
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			
		try{
			Thread.sleep(2000);
			}catch(InterruptedException E){E.printStackTrace();}
		
		jse.executeScript("window.scrollTo(0,0)");
		try{
			Thread.sleep(2000);
			}catch(InterruptedException E){E.printStackTrace();}
	
	d.close();
	}	
	
	
	
	public void buffer2()
	{
		d=new FirefoxDriver();
		d.get("https://www.proptiger.com/pune/kumar-urban-kruti-wadgaon-sheri-5000162/3bhk-3t-1368-sqft-apartment");
		d.findElement(By.xpath("//"));
	}
}
