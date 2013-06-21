package clear.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import clear.driver.TestDriver;



public class OraDBData extends TestDriver {
  
	 private static Connection connection = null;
	 static Properties dbConnProp = null;
	 
	 /**
		 * This method is to set the connection  
		 * @param uri, user id, password
		 * @return
		 * @throws 
		 * @author ptt4kor
		 */
	 public void connect(String uri, String userid, String password)
	    {
		 try {
			 //connect if not already connected
			if(connection == null)	{
				Class.forName("oracle.jdbc.driver.OracleDriver"); //Or any other driver
				connection = DriverManager.getConnection(uri, userid, password);
				ReportLog("Oracle connection successful", LogType.PASS);
				ReportLog("Connection url:"+ uri, LogType.INFO);
				ReportLog("User id: "+ userid + "Password: "+ password, LogType.INFO);
			}
			}
			catch(SQLException e ){
				e.printStackTrace();
				System.out.println( "Couldn’t get the connection!" );
				
				ReportLog("Unable to connect to DB."+ e.getMessage(), LogType.UNCOMPLETED);
				ReportLog("SQL Exception occured", LogType.TEXTLOGONLY);
			} 
		 
		 catch(Exception ex ){
				ex.printStackTrace();
				System.out.println( "Couldn’t get the connection!" + ex.getMessage() );
				ReportLog(" Exception occured" + ex.getMessage() , LogType.TEXTLOGONLY);
				ReportLog("Unable to connect to DB." + ex.getMessage(), LogType.UNCOMPLETED);
			} 
		
		
	    }
	 
	 /**
		 * This method is to clean up the connection
		 * @param criteria
		 * @return
		 * @throws 
		 * @author ptt4kor
		 */
	 public static void oraConnCleanUp(){
		 if (connection != null)
	            try
	            {
	            	connection.close();
	            	connection = null;
	            	dbConnProp.clear();
	            }
	            catch (SQLException e1)
	            {
	                e1.printStackTrace();
	            }
	 }
	 
	 /**
		 * This method is to load the config.prop 
		 * @param criteria
		 * @return
		 * @throws 
		 * @author ptt4kor
		 */
	 public  void loadConfigFile() {
		 FileInputStream fip;
 		// load config.properties
		 dbConnProp = new Properties();
		try {
			
			fip = new FileInputStream(TestDriver.configPath + "\\config.properties");
			dbConnProp.load(fip);
		
			} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
	 }
	 
	 /**
		 * This method is to fetch query result in a list of hash map 
		 * @param criteria
		 * @return
		 * @throws 
		 * @author ptt4kor
		 */
	 
	 
	 public  List<HashMap<String,String>> getQueryResult(String sql){
		 List<HashMap<String,String>> resultSet = new ArrayList<HashMap<String,String>>();
			 
			PreparedStatement ps = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			HashMap<String,String> map = null;
			String dbUri, uid, pw = null;
			
		 
		 try{
			 	loadConfigFile();
			 	
			 	dbUri = dbConnProp.getProperty("DB_URI");
			 	uid = dbConnProp.getProperty("DB_USER");
			 	pw = dbConnProp.getProperty("DB_PASSWORD");
			 	
			 	connect(dbUri,uid,pw);
			
			 	ps = connection.prepareStatement(sql);
				//ps.setLong(1,accEntId);
				rs = ps.executeQuery();
				rsmd = rs.getMetaData();
				while (rs.next())
				{
					map = new HashMap<String,String>();
					// The column count starts from 1
					for (int i = 1; i<rsmd.getColumnCount() + 1; i++ ) {
					  String key = rsmd.getColumnName(i);
					  String value = rs.getString(key);
					  //if value is null, convert to empty string
					  value = value==null?"":value;
					  
					  //put the column name as key and value in map
					  map.put(key, value);
					}
					resultSet.add(map);
				}
				
				ReportLog(resultSet.size() + " record(s) fetched from DB", LogType.PASS);
				//close the connection
				oraConnCleanUp();
		 }
		 catch(Exception e){
			 System.out.println(e.getMessage());
			 ReportLog("Unable to execute the query", LogType.UNCOMPLETED);
			 oraConnCleanUp();
		 }
		 
		 
		 return resultSet;
	 } 
}
