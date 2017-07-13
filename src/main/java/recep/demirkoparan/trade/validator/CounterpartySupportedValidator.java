package recep.demirkoparan.trade.validator;

import java.util.Arrays;
import java.util.List;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.event.ValidationListener;

/**
 *	if the counterparty (Customer) is one of the supported ones
 *	Supported counterparties (customers) are : PLUTO1, PLUTO2
 *
 * @author recepd
 */
public class CounterpartySupportedValidator implements ValidationListener {
	
	private final List<String> validCounterparties = Arrays.asList("PLUTO1","PLUTO2");

	@Override
	public String validate (Trade trade) {
		if( validCounterparties.stream().anyMatch(e -> e.equals(trade.getCustomer()))){
			return null;
		} else {
			return getMessage();
		}
	}

	public String getMessage() {
		return "Counterparty is not Supported!";
	}
    
}
