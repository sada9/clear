package clear.utils;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;


@SuppressWarnings("unused")
public class PropertyLoader {

  Properties appOR = null;
	/**
	 * This method loads all properties files to appOR object
	 * @param Folder where properties are located
	 * @return Properties 
	 * @throws 
	 * @author ptt4kor
	 */
	public Properties loadOR(final File folder) {
		appOR = new Properties();
	    for (final File file: folder.listFiles()) {
	    	loadPropertyFile(file);
	    }
	    return appOR;
	}
	
	/**
	 * Method to load perticular property file
	 * @param Property file
	 * @return
	 * @throws 
	 * @author ptt4kor
	 */
	public void loadPropertyFile(final File pFile) {
		Properties fileProp = new Properties();
		String fullFileName[] = null;
		String fileName, fileType = null;
		
		try {
			fullFileName = pFile.getName().split("\\.");
			//get file name and file type
			fileName = fullFileName[0].toString();
			fileType = fullFileName[1].toString();
			
			//load file
			InputStream flStream = new FileInputStream(pFile);
			fileProp.load(flStream);
			
			//move all properties to OR property
			movePropertiesToOR(fileProp,fileName );
			
			//close stream
			flStream.close();
			
		} 
		
		catch (IOException e) {
			System.out.println("Couldn't load "+pFile.getName());
			e.printStackTrace();
		}
	    
	}
	
	
	/**
	 * Method to copy all properties from one file to ORapp object
	 * @param Properties, propertyFileName
	 * @return
	 * @throws 
	 * @author ptt4kor
	 */
	public void movePropertiesToOR(final Properties prop, String propertyFileName) {
		System.out.println("Loading " + propertyFileName + " prop file");
		String key, value = null;
		
		// get set-view of keys
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
		      key = (String) e.nextElement();
		      value = prop.getProperty(key);
		      
		      System.out.println(propertyFileName + "." + key);
		      
		      appOR.setProperty(propertyFileName + "." + key, value);
		    }
	}
	
	public String getWebObject(final String key) {
		return 	(String) appOR.get(key);
	}

	public static void main(String[] args){
		PropertyLoader p = new PropertyLoader();
		final File folder = new File("src\\test\\pat\\or");
		p.loadOR(folder);
		//System.out.println(p.getProp("orPATMainPg.TD_XP_VAL_PATNAME"));
		//System.out.println(p.getProp("orDesktopMainPg.TD_XP_SSNSEARCH"));
	}


}
