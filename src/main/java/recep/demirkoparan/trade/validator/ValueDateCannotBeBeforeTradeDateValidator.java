package recep.demirkoparan.trade.validator;

import java.util.Date;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.entity.TradeType;
import recep.demirkoparan.trade.event.ValidationListener;

/**
 * value date cannot be before trade date
 *
 * @author recepd
 */
public class ValueDateCannotBeBeforeTradeDateValidator implements ValidationListener {
	

	@Override
	public String validate(Trade trade) {
		Date valueDate = trade.getValueDate();
		Date tradeDate = trade.getTradeDate();
		
		if(((trade.getType().equals(TradeType.Spot) || trade.getType().equals(TradeType.Forward)) && null == valueDate) || tradeDate == null){
			return getMessage();
		}
		
		if (valueDate == null || tradeDate == null ) {
            return null;
        }
		
		if(valueDate.before(tradeDate)){
			return getMessage();
		} else{
			return null;
		}
		
	}

	public String getMessage() {
		return "Value date can not be before trade date!";
	}

}
