package com.delivey.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFIle {

	  
	  public List<String> ExcelFileReading(String filePath, String fileName, String sheetName  ) throws IOException {
		  	  
		  
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
		    
		    List<String> InputDetails = new ArrayList<>();

		    for (int i = 1; i < rowCount+1; i++) {

		        Row row = guru99Sheet.getRow(i);

		         
		        InputDetails.add(row.getCell(1).getStringCellValue());
		           
		    }

		    return InputDetails;

		    }
	  }

