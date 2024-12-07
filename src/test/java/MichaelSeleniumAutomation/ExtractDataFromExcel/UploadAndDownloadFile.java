package MichaelSeleniumAutomation.ExtractDataFromExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UploadAndDownloadFile {
//	
//	private static boolean updateCell(String fileName, int row, int col, String updatedValue) throws IOException {		
//		// TODO Auto-generated method stub
//		//ArrayList<String> a=new ArrayList<String>();			
//		FileInputStream fis=new FileInputStream(fileName);		
//		XSSFWorkbook workbook=new XSSFWorkbook(fis);		
//		XSSFSheet sheet=workbook.getSheetAt(0);		
//		XSSFRow rowField = sheet.getRow(row-1);		
//		XSSFCell cellField = rowField.getCell(col-1);		
//		cellField.setCellValue(updatedValue);		
//		FileOutputStream fos= new FileOutputStream(fileName);		
//		workbook.write(fos);		
//		workbook.close();		
//		fis.close();		
//		return true;	
//	}

	@Test
	public void downLoadAndUpload() throws IOException, InterruptedException {

		System.setProperty("webdriver.chrome.driver", "/Users/michaeldjamba/Downloads/chromedriver");
		// WebDriverManager.chromedriver().setup();

		WebDriver driver = new ChromeDriver();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");

		

	     wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#downloadButton")));
		driver.findElement(By.cssSelector("#downloadButton")).click();

		DataFormatter formatter = new DataFormatter();// used to format the data from the sheet

		FileInputStream file = new FileInputStream("/Users/michaeldjamba/Downloads/download.xlsx");

		XSSFWorkbook wholeDoc = new XSSFWorkbook(file);

		XSSFSheet sheet = wholeDoc.getSheetAt(0);

		int numberOfRows = sheet.getPhysicalNumberOfRows();
		XSSFRow firstRow = sheet.getRow(0);
		int columnNumber = firstRow.getLastCellNum(); // getting the number of columns here

		Object[][] data = new Object[numberOfRows - 1][columnNumber];

		for (int i = 1; i < numberOfRows - 1; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int c = 0; c < columnNumber; c++) { // you can specify from which column you want to get data here

				System.out.print(row.getCell(c) + " \n");

				XSSFCell cell = row.getCell(c); // get the cell

				String formatedCell = formatter.formatCellValue(cell);// format the cell into a string
 
				if(formatedCell.equalsIgnoreCase("345")) {
					System.out.println("column is "+c+" row is "+i);
					cell.setCellValue("700"); // this is where im writing to the excell fill as long as i have the x and y access i can write to the feli
               
					//data[i][c] = 500; // add the data in a multi dimensional array
							
							
							
				} 

			}

		}
		FileOutputStream fileOut = new FileOutputStream("/Users/michaeldjamba/Downloads/download.xlsx");
		wholeDoc.write(fileOut);
		fileOut.close();
		file.close();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#fileinput")));
		driver.findElement(By.cssSelector("#fileinput")).sendKeys("/Users/michaeldjamba/Downloads/download.xlsx");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='animate'] div")));
	Assert.assertEquals(driver.findElement(By.cssSelector("[class*='animate'] div")).getText(),"Updated Excel Data Successfully.");	
	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class*='animate'] div")));
	
	
	List<WebElement> elements = driver.findElements(By.cssSelector("#cell-4-undefined "));

	
Boolean found =	elements.stream().anyMatch(el->el.getText().equalsIgnoreCase("700"));

Assert.assertTrue(found);
	
	
	
	

	}
}
