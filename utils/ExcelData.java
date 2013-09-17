package clear.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
* This class includes the methods related to Excel sheet
* @author ptt4kor
* @version 1.0
*/
 
public class ExcelData {
	
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
		List<HashMap<String,String>> tempDataSheet = new ArrayList<HashMap<String,String>>();
		ListIterator<HashMap<String,String>> lstIterator = null;
		
		int rowcount=0;
		int colcount=0;
			//create input stream of the file
			fs = new FileInputStream(new File(filepath));
			ws = new WorkbookSettings();
			workbook = Workbook.getWorkbook(fs, ws);
  			sheet = workbook.getSheet(0);
  			rowcount = sheet.getRows();
  			colcount = sheet.getColumns();
  			
  			//sheetdata = new Object[rowcount-1][1];

  			for(int i=0;i<rowcount-1;i++)
  			{
  				map = new HashMap<String, String>();
  				for(int j=0;j<colcount;j++){
  					//key will always be upper case
  					map.put(sheet.getCell(j, 0).getContents().toUpperCase(), sheet.getCell(j, i+1).getContents().trim());
  				}
  				tempDataSheet.add(map);
  				//sheetdata[i][0] = map;
  			}
		
  			//ignore the rows if Execute coumn is Y
  			lstIterator = tempDataSheet.listIterator();
  			
  			//TODO: handle execeptions if column header is something else or if 'Execute'
  			while(lstIterator.hasNext()){
  				HashMap<String,String> tempMap = lstIterator.next();
  				
  				if(tempMap.get("EXECUTE").equalsIgnoreCase("N") || tempMap.get("EXECUTE").trim().isEmpty()){
  					lstIterator.remove();
  				}
  			}
  			
  			sheetdata = new Object[tempDataSheet.size()][1];
  			lstIterator = tempDataSheet.listIterator();
  			//copy list data to array
  			while(lstIterator.hasNext()){
  				sheetdata[lstIterator.nextIndex()][0] = lstIterator.next();
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
