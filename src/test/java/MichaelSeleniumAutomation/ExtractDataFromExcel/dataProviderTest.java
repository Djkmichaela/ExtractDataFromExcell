package MichaelSeleniumAutomation.ExtractDataFromExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class dataProviderTest {
	 // belew im readint the data from and excel document and returning it into and array and passing dataprovider with a tag
	@Test(dataProvider = "wins")
	public void getDataFromArray(String name, String provider, String visible,String platform, String code) {

		System.out.println(name + " " + provider + " " + visible + " " + platform + " " + code);

	}
   
	@DataProvider(name="wins")
	public Object[][] dataExtraction() throws IOException  {
		
		DataFormatter formatter = new DataFormatter();// used to format the data from the sheet
		
		FileInputStream file = new FileInputStream(
				"/Users/michaeldjamba/Lithium/Source/app-lithium-full/service-games/service-games/sql/20171026 All Games.xlsx");

		XSSFWorkbook wholeDoc = new XSSFWorkbook(file);

		XSSFSheet sheet = wholeDoc.getSheetAt(0);

		int numberOfRows = sheet.getPhysicalNumberOfRows();
		XSSFRow firstRow = sheet.getRow(0);
		int columnNumber = firstRow.getLastCellNum();  // getting the number of columns here

		Object[][] data= new Object[numberOfRows - 1][columnNumber];

		for (int i = 1; i < numberOfRows - 1; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int c = 0; c < columnNumber; c++) {    // you can specify from which column you want to get data here
				
				//System.out.print(row.getCell(c)+" \n");
				
				XSSFCell  cell =row.getCell(c);    // get the cell
				
				formatter.formatCellValue(cell); // format the cell into a string
				data[i][c] = formatter.formatCellValue(cell);   // add the data in a multi dimensional array
			}

		}
		
		
        return data;
		
	}
	
	
	// below im sending data with a declared array and and im linking the data provider using the method name

	@Test(dataProvider = "sendData")
	public void getsData(String name, String surname, int id) {

		System.out.println(name + " " + surname + " " + id);

	}

	@DataProvider()
	public Object[][] sendData() {

		Object[][] wins = { { "Michael", "Djamba", 32 }, { "Lisa", "Smith", 1 }, { "babalwa", "vas", 21 } };

		return wins;

	}

}
