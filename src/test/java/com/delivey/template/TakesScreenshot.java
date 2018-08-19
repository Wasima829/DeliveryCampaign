package com.delivey.template;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import java.io.FileOutputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TakesScreenshot {
	public WebDriver secondDriver1;	
	public TakesScreenshot(WebDriver driver){
		this.secondDriver1= driver;
		
	}
	
	public void allPage(List<String> xyz, List<String> pqr, String StoreLocation, String Delivery_name) throws InterruptedException  {	
		secondDriver1 = new ChromeDriver();		
	for (int i=1; i<xyz.size();i++){
		String URLTEST = xyz.get(i);
		String filename= pqr.get(i);
		if(URLTEST!=null ){
		secondDriver1.navigate().to(URLTEST);
		//secondDriver1.manage().window().maximize();setSize(new Dimension(1440, 900))
		//secondDriver1.manage().window().setSize(new Dimension(1440, 900));
		secondDriver1.manage().window().maximize();
		Thread.sleep(2000);
		

		//File scrFile = ((TakesScreenshot) secondDriver1).getScreenshotAs(OutputType.FILE);	
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		/*try {
			//FileUtils.copyFile(scrFile, new File( StoreLocation + Delivery_name + "\\"+filename+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		secondDriver1.navigate().back();
		Thread.sleep(1000);
		}
		else {System.out.println("Not loaded the URL as it is null");}
	}
		
		
	}
	
	
}
