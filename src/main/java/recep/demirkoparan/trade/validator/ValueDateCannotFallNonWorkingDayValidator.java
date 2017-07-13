package recep.demirkoparan.trade.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import com.jayway.jsonpath.JsonPath;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.entity.TradeType;
import recep.demirkoparan.trade.event.ValidationListener;
import recep.demirkoparan.trade.util.DateUtil;

/**
 * value date cannot fall on weekend or non-working day for currency
 * (candidate can use any public services e.g. http://fixer.io/ )
 *
 * @author recepd
 */
public class ValueDateCannotFallNonWorkingDayValidator implements ValidationListener {
	
	@Override
	public String validate(Trade trade) {		
		if((trade.getType().equals(TradeType.Spot) || trade.getType().equals(TradeType.Forward)) && null == trade.getValueDate()){
			return getMessage();
		}
		if(null == trade.getValueDate()){
			return null;
		}
        LocalDate localDate = DateUtil.convertDateToLocalDate(trade.getValueDate());
        if (localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return getMessage();
        }
        
        boolean isInHoliday = isInHoliday(trade.getValueDate());
        
        if(isInHoliday){
        	return getMessage();
        }
        
        return null;
	}
	
	public static void main(String[] args) {
		ValueDateCannotFallNonWorkingDayValidator v = new ValueDateCannotFallNonWorkingDayValidator();
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 0, 1);
		v.isInHoliday(cal.getTime());
	}

	private boolean isInHoliday(Date valueDate) {
		try {

			final String dateAsString = DateUtil.getDateAsString(valueDate);
			
			URL url = new URL("http://api.fixer.io/" + dateAsString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = br.readLine();
			if(null != output){
				String date = JsonPath.read(output, "$.date");
				return !date.equals(dateAsString);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return false;
	}

	public String getMessage() {
		return "Value date cannot be non working day!";
	}

}
