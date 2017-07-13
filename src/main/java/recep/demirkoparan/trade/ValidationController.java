package recep.demirkoparan.trade;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import recep.demirkoparan.trade.entity.Trade;
import recep.demirkoparan.trade.entity.ValidationResult;
/**
*
* @author recepd
*/
@RestController
public class ValidationController {
	
	private final static transient Logger LOG = Logger.getLogger(ValidationController.class);
	
	@PostConstruct
    private void init() {
        LOG.debug("Rest service is up");
    }

    @PreDestroy
    private void end() {
        LOG.debug("Rest service is down");
    }

    @RequestMapping(value="/trade/validate", method=RequestMethod.POST)
	public ResponseEntity<?> validate(@RequestBody Trade trade)
			throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<?> retVal;
		if (null != trade) {
			ValidationResult result = Validator.validate(trade);
			if (result.getMessages().isEmpty()) {
				retVal = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				retVal = new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			retVal = new ResponseEntity<>("Empty trade request not allowed!", HttpStatus.NO_CONTENT);
		}
		return retVal;
	}

    @RequestMapping(value="/trade/validatebulk", method=RequestMethod.POST)
    public List<ValidationResult> validateBulk(@RequestBody List<Trade> trades) throws JsonParseException, JsonMappingException, IOException {
    	return Validator.validate(trades);
    }
}
