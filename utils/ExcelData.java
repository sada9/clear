package clear.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
* This class includes the methods related to Excel sheet
* @author ptt4kor
* @version 1.0
*/
 
public class ExcelData {
  //column number for checking condition 1
	public static final int col1=0;
	//column number for checking condition 2
	public static final int col2=1;
	//column number for checking condition 3
	public static final int col3=2;
	//column number for checking condition 4
	public static final int col4=3;
	//column number for checking condition 5
	public static final int col5=4;
	//column number for checking condition 6
	public static final int col6=5;
	//input column number
	public static final int input_col=6;
	//output column number
	public static final int output_col=0;
	
	/**
	 * This method loads the excel data into array of HashMap 
	 * @param filepath
	 * @throws Exception 
	 * @author ptt4kor
	 * 
	 */
	public Object [][] GetSheetData(String filepath) throws Exception
	{
		FileInputStream fs = null;
		WorkbookSettings ws = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Object [][] sheetdata=null;
		HashMap<String, String> map = null;
		int rowcount=0;
		int colcount=0;
			//create input stream of the file
			fs = new FileInputStream(new File(filepath));
			ws = new WorkbookSettings();
			workbook = Workbook.getWorkbook(fs, ws);
  			sheet = workbook.getSheet(0);
  			rowcount = sheet.getRows();
  			colcount = sheet.getColumns();
  			sheetdata = new Object[rowcount-1][1];

  			for(int i=0;i<rowcount-1;i++)
  			{
  				map = new HashMap<String, String>();
  				for(int j=0;j<colcount;j++)
  				{
  					map.put(sheet.getCell(j, 0).getContents(), sheet.getCell(j, i+1).getContents());
  				}
  				System.out.println();
  				sheetdata[i][0] = map;
  			}
		
  		return sheetdata;
	}
	
	
	/**
	 * Method to get the row count in excel sheet
	 * @param filepath
	 * @throws Exception 
	 * @author ptt4kor
	 * 
	 */
	public int getRowcount(String filepath) throws Exception
	{
		int count = 0;
		FileInputStream fs = new FileInputStream(new File(filepath));
		Workbook workbook = Workbook.getWorkbook(fs);
		Sheet sheet = workbook.getSheet(0);
		count = sheet.getRows();
		fs.close();
		return count;
	}
	
	/**
	 * Method to get the column count in excel sheet
	 * @param filepath
	 * @throws Exception 
	 * @author ptt4kor
	 * 
	 */
	public int getColunmcount(String filepath) throws Exception
	{
		int count = 0;
		FileInputStream fs = new FileInputStream(new File(filepath));
		Workbook workbook = Workbook.getWorkbook(fs);
		Sheet sheet = workbook.getSheet(0);
		count =  sheet.getColumns();
		return count;
	}
} 
