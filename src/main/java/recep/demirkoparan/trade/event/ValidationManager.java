package recep.demirkoparan.trade.event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import recep.demirkoparan.trade.entity.Trade;
/**
*
* @author recepd
*/
public class ValidationManager {

	private List<ValidationListener> listeners = new LinkedList<> ();

	public void addListener(ValidationListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(ValidationListener listener) {
		this.listeners.remove(listener);
	}

	public List<String> validate(Trade trade) {
		List<String> results = new ArrayList<>();
		String result;
		for (ValidationListener listener : listeners) {
			result = listener.validate(trade);
			if(null != result){
				results.add(result);
			}
		}
		return results;
	}
	
	
}
