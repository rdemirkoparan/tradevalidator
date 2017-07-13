package recep.demirkoparan.trade.validator;

import java.util.Date;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.entity.TradeType;
import recep.demirkoparan.trade.event.ValidationListener;

/**
 *
 *	expiry date and premium date shall be before delivery date
 *
 * @author recepd
 */
public class ExpiryAndPremiumDateBeforeDeliveryDateValidator implements ValidationListener {

	@Override
	public String validate (Trade trade) {
		if(trade.getType().equals(TradeType.VanillaOption)){
			Date premiumDate = trade.getPremiumDate();
			Date expiryDate = trade.getExpiryDate();
			Date deliveryDate = trade.getDeliveryDate();
			
			if (expiryDate == null || premiumDate == null || deliveryDate == null) {
	            return null;
	        }
			
			if(expiryDate.before(deliveryDate) && premiumDate.before(deliveryDate)){
				return null;
			} else {
				return getMessage();
			}
		}
		
		return null;
	}

	public String getMessage() {
		return "Expiry date and premium date shall be before delivery date!";
	}

}
