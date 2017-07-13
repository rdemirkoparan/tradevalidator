package recep.demirkoparan.trade.validator;

import java.util.Currency;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.event.ValidationListener;
import recep.demirkoparan.trade.util.StringUtil;

/**
 * 
 *	validate currencies if they are valid ISO codes ( ISO 4217)
 *  https://en.wikipedia.org/wiki/ISO_4217
 *
 * @author recepd
 */
public class CurrenciesISO4217Validator implements ValidationListener {

	@Override
	public String validate(Trade trade) {
		String ccyPair = trade.getCcyPair();
		
		String from = ccyPair.substring(0, 3);
        String to = ccyPair.substring(3);
        
        if (StringUtil.isNullOrEmpty(from) || StringUtil.isNullOrEmpty(to)) {
            return getMessage();
        }
        
        try {
        	Currency.getInstance(from);
        	Currency.getInstance(to);
		} catch (IllegalArgumentException e) {
			return getMessage();
		}
        
		return null;
	}

	public String getMessage() {
		return "Currency not supported!";
	}
    
}
