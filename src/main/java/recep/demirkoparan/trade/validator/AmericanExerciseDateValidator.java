package recep.demirkoparan.trade.validator;

import java.util.Date;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.entity.TradeStyle;
import recep.demirkoparan.trade.entity.TradeType;
import recep.demirkoparan.trade.event.ValidationListener;

/**
 *
 *	American option style will have in addition the excerciseStartDate, which has to be after the trade date but before the expiry date
 *
 * @author recepd
 */
public class AmericanExerciseDateValidator implements ValidationListener {

	@Override
	public String validate (Trade trade) {
		
		if(trade.getType().equals(TradeType.VanillaOption) && trade.getStyle().equals(TradeStyle.AMERICAN)){
			Date excerciseStartDate = trade.getExcerciseStartDate();
			Date expiryDate = trade.getExpiryDate();
			Date tradeDate = trade.getTradeDate();
			
			if (expiryDate == null || excerciseStartDate == null || tradeDate == null) {
	            return null;
	        }
			
			if( excerciseStartDate.before(expiryDate) && excerciseStartDate.after(tradeDate)){
				return null;
			} else {
				return getMessage();
			}
		}
		
		return null;
	}

	public String getMessage() {
		return "Excercise start date has to be after the trade date and before the expiry date!";
	}

}
