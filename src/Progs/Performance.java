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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class Performance 
{	
	Object[][] ob;
	FileInputStream fin;
	HSSFWorkbook wb;
	HSSFSheet sh;
	HSSFCell cell;
	HSSFRow row;
	@DataProvider(name="ipURLs")
	public Object[][] inputURLs()
	{
		
		try
		{
			fin = new FileInputStream("performance.xls");
			wb = new HSSFWorkbook(fin);
			sh=wb.getSheetAt(0);
			int lrow=sh.getLastRowNum();
			ob = new Object[lrow+1][1];
			for (int i=1;i<=lrow;i++)
			{
				row=sh.getRow(i);
				cell=row.getCell(0);
				ob[i-1][0]=cell.getStringCellValue();
			}
		}catch(IOException e){System.out.println("Unable to Locate file");}
		return ob;				
	}	
	
	@Test(dataProvider="ipURLs")
	public void checkPerformance(String url) throws InterruptedException
	{
		WebDriver d = new FirefoxDriver();
		d.get("http://www.webpagetest.org");
		WebElement URL=d.findElement(By.id("url"));
		URL.sendKeys(url);
		WebElement startbutton = d.findElement(By.xpath("//input[@type='submit']"));
		startbutton.click();
		d.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		try
		{
			d.findElement(By.xpath("//input[@value='Re-run the test']"));
		}catch(Exception E){System.out.println("kuch mila");}
		afterResult(d,url);
	}
	
	public void afterResult(WebDriver d,String url)
	{
		List<WebElement> fv= d.findElements(By.xpath("//table[@id='tableResults']/tbody/tr[3]/td"));
		List<WebElement> rv= d.findElements(By.xpath("//table[@id='tableResults']/tbody/tr[4]/td"));
		try
		{
			sh=wb.getSheetAt(1);
			int lrow=sh.getLastRowNum();
			row = sh.createRow(lrow+1);
			cell = row.createCell(0);
			cell.setCellValue(url);
			int n = 1;
			for (WebElement e : fv)
			{
				cell = row.createCell(n);
				cell.setCellValue(e.getText());
				n+=1;
			}
			cell = row.createCell(n);
			cell.setCellValue(d.getCurrentUrl());
			row = sh.createRow(lrow+2);
			cell = row.createCell(0);
			cell.setCellValue("");
			n = 1;
			for (WebElement e : rv)
			{
				cell = row.createCell(n);
				cell.setCellValue(e.getText());
				n+=1;
			}
			FileOutputStream fileout = new FileOutputStream("performance.xls");
			wb.write(fileout);    
			fileout.flush();
			fileout.close();
			fin.close();	
			d.close();
		}catch(IOException E){System.out.println("Unable to locate file");}
	}
}
	