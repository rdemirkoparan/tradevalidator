package recep.demirkoparan.trade.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author recepd
 */
public class DateUtil {
    
     private static String zurichTimeZone = "Europe/Zurich";
    
    public static LocalDate convertDateToLocalDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(TimeZone.getTimeZone(zurichTimeZone).toZoneId()).toLocalDate();
        return localDate;
    }
    
    public static String getDateAsString(Date date){
    	String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    	return formattedDate;
    }
}
