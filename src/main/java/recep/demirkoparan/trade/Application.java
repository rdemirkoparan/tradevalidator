package recep.demirkoparan.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import recep.demirkoparan.trade.event.ValidationManager;
import recep.demirkoparan.trade.validator.AmericanExerciseDateValidator;
import recep.demirkoparan.trade.validator.CounterpartySupportedValidator;
import recep.demirkoparan.trade.validator.CurrenciesISO4217Validator;
import recep.demirkoparan.trade.validator.ExpiryAndPremiumDateBeforeDeliveryDateValidator;
import recep.demirkoparan.trade.validator.LegalEntityValidator;
import recep.demirkoparan.trade.validator.ValueDateCannotBeBeforeTradeDateValidator;
import recep.demirkoparan.trade.validator.ValueDateCannotFallNonWorkingDayValidator;
/**
*
* @author recepd
*/
@SpringBootApplication
public class Application {
	
	private static ValidationManager manager = new ValidationManager ();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        manager.addListener(new AmericanExerciseDateValidator());
        manager.addListener(new CounterpartySupportedValidator());
        manager.addListener(new CurrenciesISO4217Validator());
        manager.addListener(new ExpiryAndPremiumDateBeforeDeliveryDateValidator());
        manager.addListener(new LegalEntityValidator());
        manager.addListener(new ValueDateCannotBeBeforeTradeDateValidator());
        manager.addListener(new ValueDateCannotFallNonWorkingDayValidator());
    }

	public static ValidationManager getManager() {
		return manager;
	}
}
