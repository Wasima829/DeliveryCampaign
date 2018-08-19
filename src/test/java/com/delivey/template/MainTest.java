package com.delivey.template;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openqa.selenium.TakesScreenshot;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import org.testng.annotations.Test;

import com.google.common.io.Files;

public class MainTest  {
	
	public String StoreLocation = null;
	public String Delivery_name=null;;
	public WebDriver driver;
	public WebDriver Seconddriver;
    public String Url= null;	
	HttpURLConnection huc = null;
	int ResponseCOde = 200;	
	public String FILEPath= "C:\\";
	public String FILEName="CampaignDelivery.xlsx";
	public String SHEETName= "InputDetails";

	@BeforeTest
	
	public void LaaunchBrowser() {
		
		ReadFIle readObject= new ReadFIle();
		
		try {
			List<String> InputDetailsFromFile =readObject.ExcelFileReading(FILEPath, FILEName, SHEETName);
			
			Url= InputDetailsFromFile.get(0);
			StoreLocation= InputDetailsFromFile.get(1);
			Delivery_name= InputDetailsFromFile.get(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		  File file = new File(StoreLocation + Delivery_name);
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                System.out.println("Directory is created!");
	            } 
	            else if((file.exists())) {
	                System.out.println("Directory already exist");
	            }
	            else {
	            	System.out.println("Failed to create directory!");
	            }
	        }		
		driver = new ChromeDriver();		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(Url);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);		
		try {
			FileUtils.copyFile(scrFile, new File(StoreLocation + "\\"+Delivery_name+"\\"+"DeliveryTemplate.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void CloseBrowser() {
		driver.close();
	}

	@Test (priority=0)
	public void ALLBrokenLinkTest() throws InterruptedException, IOException {
		List<WebElement> AllLinks = driver.findElements(By.tagName("a"));
		List<List<String>> urlList = new ArrayList<>();
		int size = AllLinks.size();		
		int count =0;
		List<String> list11 = new ArrayList<>();
		list11.add("Labels Of the URL");
		list11.add("URL");
		list11.add("URL Connection Status");
		list11.add("Response COde Details");
		urlList.add(list11);
		for (int i = 0; i < size; i++) {
			List<String> list = new ArrayList<>();
			WebElement t = AllLinks.get(i);
			Url = t.getAttribute("href");
			String s = t.getText();			
			if (s.isEmpty()){ 
				s = t.getAttribute("_label");				
			}			
			if (Url == null || Url.isEmpty()) {
				
				list.add(++count +"."+s);
				list.add(Url);
				list.add("URL is empty or null");
				list.add("Url Null,hence no response code");
				
//				list.forEach(System.out::println);
				urlList.add(list);
				
			} else if (!Url.startsWith(Url)) {
				// This means it is so someother domails
				
				list.add(++count +"."+s);
				list.add(Url);
				list.add("other domain Link");
				list.add("Other domain URL lInk");
				urlList.add(list);
//				list.forEach(System.out::println);

			} else if (Url != null) {
				try {
					huc = (HttpURLConnection) (new URL(Url).openConnection());
					huc.setRequestMethod("HEAD");
					huc.connect();
					int ResponseCOde = huc.getResponseCode();
					if (ResponseCOde >= 400) {
						String ResponseCodeVal= String.valueOf(ResponseCOde);
					
					// means it is a Broken link");					
						list.add(++count +"."+s);
						list.add(Url);
						list.add("Broken Link");
						list.add(ResponseCodeVal);
						urlList.add(list);
						
												
						System.out.println(ResponseCOde);
//						list.forEach(System.out::println);
					} else {
					
					// it is a Working link");
						String ResponseCodeVal= String.valueOf(ResponseCOde);

						list.add(++count +"."+s);
						list.add(Url);
						list.add("Working  Link");
						list.add(ResponseCodeVal);
						urlList.add(list);
						System.out.println(ResponseCOde);
//						list.forEach(System.out::println);
					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			}

			
		}
		
		//urlList.forEach(System.out::println);
		
		List<String> urlStore = new ArrayList<>();
		for(List<String> list1 : urlList) {
			if (list1.get(1) == null)
				urlStore.add(null);
			else
				urlStore.add(list1.get(1).toString());
		}
		//urlStore.forEach(System.out::println);
				
		List<String> NameStore = new ArrayList<>();
		for(List<String> list2 : urlList) {			
			if (list2.get(0) == null)
				NameStore.add("NO Lable is there");
			else
				NameStore.add(list2.get(0).toString());
		}
		//NameStore.forEach(System.out::println);
		
		StroreinExcel ThirdCLass= new StroreinExcel();
		ThirdCLass.StoreExcel(urlList,StoreLocation, Delivery_name);		
		/*TakesScreenshot secondClass= new TakesScreenshot(Seconddriver);		
		secondClass.allPage(urlStore,NameStore,StoreLocation,Delivery_name);*/		
		AllLinkScreenshot secondClass= new AllLinkScreenshot(Seconddriver);		
		secondClass.allPage(urlStore,NameStore,StoreLocation,Delivery_name);
	}

}
class StroreinExcel{
	public void StoreExcel(List<List<String>> RecivedList,String StoreLocation, String Delivery_name) throws IOException{
		FileOutputStream out = new FileOutputStream(new File(StoreLocation +"\\"+ Delivery_name+"\\"+"BrokenLinkDetails.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("LinkDetails");
        Iterator <List<String>>i = RecivedList.iterator();
        int rownum=0;
        int cellnum = 0;
        while (i.hasNext()) {
            List<String> templist = (List<String>) i.next();
            Iterator<String> tempIterator= templist.iterator();
            Row row = sheet.createRow(rownum++);
            cellnum = 0;
            CellStyle style = workbook.createCellStyle();    	      
    	    style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index);
    	    
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = workbook.createFont();
			font.setFontName("Arial");
            font.setColor(IndexedColors.RED.getIndex());
            style.setFont(font);
    	    
            while (tempIterator.hasNext()) {
                String temp = (String) tempIterator.next();
                    Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(temp);
                            if(rownum==1){
                            	cell.setCellStyle(style);
                            }
                    	    

                }

            }
        workbook.write(out);
        out.close();
        workbook.close();

		
	}
}

class  AllLinkScreenshot{
	public WebDriver secondDriver1;
//	public String Url = "https://southwest-airlines-mkt-stage1-m.campaign.adobe.com/nl/jsp/m.jsp?c=%40bVtUpbo%2FdPZnYvYRJr%2Fji0IiPb2hcMaDAG45jN43TGM%3D&RR_NUMBER=a7357950076a607d8b4f759017a517779cb73026&RSD=0000&RMID=AC_Pretrip_Cuba&RRID=cd318930ca02ee833f75e634a25e630c4520696e0ee78b580a560bf8d8169721&src=MAILTXNPRETBKCUBA161228";
	String Path = "C:\\Users\\akram\\Desktop\\Projects\\Eclipse_Selenium\\chromedriver_win32\\New V2.38\\chromedriver.exe";
	
	public AllLinkScreenshot(WebDriver driver){
		this.secondDriver1= driver;
		
	}
	
	public void allPage(List<String> xyz, List<String> pqr, String StoreLocation, String Delivery_name) throws InterruptedException  {	
		System.setProperty("webdriver.chrome.driver", Path);
		secondDriver1 = new ChromeDriver();
		
	for (int i=1; i<xyz.size();i++){
		String URLTEST = xyz.get(i);
		String filename= pqr.get(i);
		if(URLTEST!=null ){
		secondDriver1.navigate().to(URLTEST);
		//secondDriver1.manage().window().maximize();setSize(new Dimension(1440, 900))
		//secondDriver1.manage().window().setSize(new Dimension(1440, 900));
		secondDriver1.manage().window().fullscreen();
		Thread.sleep(2000);
		
		File scrFile = ((TakesScreenshot) secondDriver1).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File( StoreLocation + "\\"+Delivery_name + "\\"+filename+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		secondDriver1.navigate().back();
		Thread.sleep(1000);
		}
		else {System.out.println("Not loaded the URL as it is null");}
	}
		
	secondDriver1.close();
		
		
	}
	
	
}


