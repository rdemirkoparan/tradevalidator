package recep.demirkoparan.trade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.entity.ValidationResult;
import recep.demirkoparan.trade.util.MetricsUtil;
/**
*
* @author recepd
*/
public class Validator {
	
	private static final AtomicLong counter = new AtomicLong();
	
	private final static transient Logger LOG = Logger.getLogger(Validator.class);
    
    private static StopWatch watch = new StopWatch();
    private static MetricsUtil metrics = MetricsUtil.getInstance();

	public static ValidationResult validate(Trade trade) throws JsonParseException, JsonMappingException, IOException {
		
		List<String> result = Application.getManager().validate(trade);
		
		return new ValidationResult().start().incrementIndex(counter.incrementAndGet()).addTradeData(trade.toString()).addMessages(result).end();
		
	}

	public static List<ValidationResult> validate(List<Trade> trades) throws JsonParseException, JsonMappingException, IOException {
		
		try {
            watch.start();
        
            List<ValidationResult> result = new ArrayList<>();
    		trades.stream().forEach(trade -> {
    			try {
    				result.add(validate(trade));
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		});
    		return result;
    		
        } finally {
            watch.stop();
            metrics.gauge (watch.getTotalTimeSeconds(), watch.getLastTaskTimeMillis(), trades.size());
            LOG.info(metrics);
        }
		
		
	}

}
