package recep.demirkoparan.trade.event;

import recep.demirkoparan.trade.entity.Trade;
/**
*
* @author recepd
*/
public interface ValidationListener {

	public String validate(Trade trade);
	
}