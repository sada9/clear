package clear.utils;

import java.util.logging.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
* This class formats the log message and date
* @author ptt4kor
* @version 1.0
*/
public class LogFormatter extends Formatter {
	
	
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
    /**
	 * Method to format log record
	 * @param message to be printed on report, LogType 
	 * @return type of log could be any one of LogType
	 * @throws 
	 * @author ptt4kor
	 */
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        
        builder.append(formatMessage(record));
        builder.append(System.getProperty("line.separator"));
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }

}
