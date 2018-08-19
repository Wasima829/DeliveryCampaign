package com.delivey.template;

import java.io.File;

import org.testng.annotations.Test;

public class ReadExcelFile {
  
  public void ExcelFileReading() {
	  public Strign filePath= "C:\\";
	  public Strign fileName="CampaignDelivery.xlsx";	  
	  
	  File file =    new File(filePath+"\\"+fileName);

	    //fileInputstream object created

	    FileInputStream inputStream = new FileInputStream(file);

	    Workbook guru99Workbook = null;

	    String fileExtensionName = fileName.substring(fileName.indexOf("."));

	    //Check condition if the file is xlsx file

	    if(fileExtensionName.equals(".xlsx")){

	    //If it is xlsx file then create object of XSSFWorkbook class

	    guru99Workbook = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file

	    else if(fileExtensionName.equals(".xls")){

	        //If it is xls file then create object of XSSFWorkbook class

	        guru99Workbook = new HSSFWorkbook(inputStream);

	    }

	    //Read sheet inside the workbook by its name

	    Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

	    //Find number of rows in excel file

	    int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();

	    //Create a loop over all the rows of excel file to read it

	    for (int i = 1; i < rowCount+1; i++) {

	        Row row = guru99Sheet.getRow(i);

	        //Create a loop to print cell values in a row

	      

	            //Print Excel data in console

	            System.out.println(row.getCell(2).getStringCellValue());        

	      

	    }

	    

	    }
  }
}
