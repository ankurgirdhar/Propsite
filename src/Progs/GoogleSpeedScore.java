package Progs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GoogleSpeedScore 
{
	WebDriver d;
	FileInputStream fin;
	HSSFWorkbook wb;
	HSSFSheet sh;
	HSSFCell cell;
	HSSFRow row;
	
	@BeforeTest
	public void init()
	{
		d = new FirefoxDriver();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void closeBrowser()
	{
		d.close();
		d.quit();
	}
	
	@Test
	public void writeScore()
	{
		try
		{
			int i,rowlen,lrow;
			String url;
			fin = new FileInputStream("performance.xls");
			wb = new HSSFWorkbook(fin);
			sh=wb.getSheetAt(2);
			lrow=sh.getLastRowNum();
			rowlen=sh.getRow(2).getLastCellNum();			//assume that 1st row has maximum columns, hence computing this one only
			for (i=2;i<=lrow;i++)
			{
				row=sh.getRow(i);
				cell=row.getCell(0);
				url=cell.getStringCellValue();
				cell = sh.getRow(i).createCell(rowlen);
				cell.setCellValue(getGoogleScore(url));
				
			}
			FileOutputStream fileout = new FileOutputStream("performance.xls");
			wb.write(fileout);    
			fileout.flush();
			fileout.close();
			fin.close();
		}catch(IOException e){e.printStackTrace();}
	}		
	
	public String getGoogleScore(String url)
	{
		System.out.print(url + "\t");
		url= "https://developers.google.com/speed/pagespeed/insights/?url=".concat(url).concat("&tab=desktop");
		d.get(url);
		List<WebElement> scores = d.findElements(By.xpath("//h2[1]/span[1]"));
		System.out.println(scores.get(scores.size()-1).getText());
		return(scores.get(scores.size()-1).getText());
	}
}
