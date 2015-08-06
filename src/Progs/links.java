package Progs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class links 
{
	
	boolean isfirsttime=true;
	WebDriver d;
	Object[][] ob;
	FileInputStream fin;
	HSSFWorkbook wb;
	HSSFSheet sh;
	HSSFCell cell,cell2;
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
	
	@DataProvider(name="ipURLs")
	public Object[][] inputURLs()
	{
		try
		{
			int i;
			fin = new FileInputStream("links.xls");
			wb = new HSSFWorkbook(fin);
			sh=wb.getSheetAt(0);
			int lrow=sh.getLastRowNum();
			ob = new Object[lrow][1];
			for (i=1;i<=lrow;i++)
			{
				row=sh.getRow(i);
				cell=row.getCell(0);
				ob[i-1][0]=cell.getStringCellValue();
			}
		System.out.println("Total URLs for which links will be derived = " + --i);
		}catch(IOException e){e.printStackTrace();}
		return ob;				
	}	

	public List<String> findLinks(String url)
	{
		String lnk;
		d.get(url);
		try
		{
		Thread.sleep(5000);
		}catch(InterruptedException E){E.printStackTrace();}
		List<WebElement> links = d.findElements(By.tagName("a"));
		List<String>  st= new ArrayList<String>();
		for (WebElement e : links)
		{
			lnk=e.getAttribute("href");
			st.add(lnk);
		}
		System.out.println("Total number of links present on this page = " + links.size());
		return st;
	}
	
	public void writeStatus(String url,List<String> links)
	{
		int count=0,urlstatus,lrow;	
		try
		{
			sh=wb.getSheetAt(1);
			if(isfirsttime)
			{
				lrow=0;
				isfirsttime=false;
			}
			else
			lrow=sh.getLastRowNum();
			row = sh.createRow(lrow+1);
			cell = row.createCell(0);
			cell.setCellValue("List of links present on " + url);
			for (String lnk : links)
			{
				row = sh.createRow(lrow+count+2);
				urlstatus=getURLStatus(lnk);
				cell = row.createCell(1);
				cell2 = row.createCell(2);
				if(urlstatus==200)
				{
					cell.setCellValue(lnk);
					cell2.setCellValue(urlstatus);
					System.out.println(count + 1 + ". " + lnk + "   " + urlstatus);
				}
				else
				{
					cell.setCellValue(lnk);
					cell2.setCellValue(urlstatus);
					System.err.println(count + 1 + ". " + lnk + "    " + urlstatus);					
				}
				
				count++;
			}			
			FileOutputStream fileout = new FileOutputStream("links.xls");
			wb.write(fileout);    
			fileout.flush();
			fileout.close();
			fin.close();
			
		}catch(IOException E){E.printStackTrace();}			
	}
	
	public int getURLStatus(String add)
	{
		int code=0;
		HttpURLConnection connection;
		try
		{
		URL url = new URL(add);
		connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.setInstanceFollowRedirects(false);     //To disable redirects else 301 will also give 200
		connection.connect();
		code = connection.getResponseCode();
		}catch(IOException E){E.printStackTrace();}
		return code;
	}
	
	@Test(dataProvider="ipURLs")
	public void getLinksStatus(String url)
	{
		List<String> links = new ArrayList<String>();
		System.out.println("Finding links for " + url);
		if(getURLStatus(url)!=200)
		{
			System.err.println(url + " is not valid URL, it is giving status " + getURLStatus(url));
			return;
		}
		links = findLinks(url);
		writeStatus(url,links);
		System.out.println("links for " + url + " are  done");
	}
	
	
}
