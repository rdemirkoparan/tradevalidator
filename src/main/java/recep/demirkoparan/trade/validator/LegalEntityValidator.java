package recep.demirkoparan.trade.validator;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.event.ValidationListener;

/**
 *
 *	Only one legal entity is used: CS Zurich
 *
 * @author recepd
 */
public class LegalEntityValidator implements ValidationListener {

    private final String validLegalEntity = "CS Zurich";

	@Override
	public String validate(Trade trade) {
		if(!validLegalEntity.equals(trade.getLegalEntity())){
			return getMessage(); 
		} else {
			return null;
		}
	}

	public String getMessage() {
		return "Only " + validLegalEntity + " is allowed!";
	}
    
}
