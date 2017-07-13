package recep.demirkoparan.trade.entity;

import java.util.ArrayList;
import java.util.List;
/**
*
* @author recepd
*/
public class ValidationResult {

	private long index;
	private String tradeData;
	private List<String> messages;
	
	public ValidationResult() {
	}
	
	public ValidationResult start() {
		return this;
	}
	
	public ValidationResult incrementIndex(long _index) {
		this.index = _index;
		return this;
	}
	
	public ValidationResult addTradeData(String _tradeData) {
		this.tradeData = _tradeData;
		return this;
	}
	
	public ValidationResult addMessage(String message) {
		if(null == this.messages){
			this.messages = new ArrayList<String>();
		}
		this.messages.add(message);
		return this;
	}
	
	public ValidationResult addMessages(List<String> _messages) {
		if(null == this.messages){
			this.messages = new ArrayList<String>();
		}
		this.messages.addAll(_messages);
		return this;
	}
	
	public ValidationResult end() {
		return this;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getTradeData() {
		return tradeData;
	}

	public void setTradeData(String tradeData) {
		this.tradeData = tradeData;
	}
}
