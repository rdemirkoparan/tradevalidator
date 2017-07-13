package recep.demirkoparan.trade;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

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
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ValidateTradeTest {

    private static final String VALIDATEBULKURL = "/trade/validatebulk";
	private static final String VALIDATEURL = "/trade/validate";
	private static final ValueDateCannotFallNonWorkingDayValidator valueDateCannotFallNonWorkingDayValidator = new ValueDateCannotFallNonWorkingDayValidator();
	private static final ValueDateCannotBeBeforeTradeDateValidator valueDateCannotBeBeforeTradeDateValidator = new ValueDateCannotBeBeforeTradeDateValidator();
	private static final LegalEntityValidator legalEntityValidator = new LegalEntityValidator();
	private static final ExpiryAndPremiumDateBeforeDeliveryDateValidator expiryAndPremiumDateBeforeDeliveryDateValidator = new ExpiryAndPremiumDateBeforeDeliveryDateValidator();
	private static final CurrenciesISO4217Validator currenciesISO4217Validator = new CurrenciesISO4217Validator();
	private static final CounterpartySupportedValidator counterpartySupportedValidator = new CounterpartySupportedValidator();
	private static final AmericanExerciseDateValidator americanExerciseDateValidator = new AmericanExerciseDateValidator();

	private static final String BULKNOERROR = "[{\"customer\":\"PLUTO2\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\",\"direction\":\"SELL\",\"strategy\":\"CALL\",\"tradeDate\":\"2016-08-12\",\"valueDate\":\"2016-08-13\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"},{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\",\"direction\":\"SELL\",\"strategy\":\"CALL\",\"tradeDate\":\"2016-08-12\",\"valueDate\":\"2016-08-13\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}]";
	private static final String SINGLENOERROR = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-08-15\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String AMERICANEXERCISEDATE = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\",\"direction\":\"SELL\",\"strategy\":\"CALL\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2016-08-22\",\"expiryDate\":\"2016-08-19\",\"excerciseStartDate\":\"2016-08-20\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2016-08-12\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String COUNTRYPARTYNOTSUPPORTED = "{\"customer\":\"PLUTO3\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"SELL\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-08-22\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String CURRENCIESNOTVALIDISO4217 = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"ABCDEF\",\"type\":\"Spot\",\"direction\":\"SELL\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-08-22\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String EXPIRYANDPREMIUMDATENOTBEFOREDELIVERYDATE = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2016-08-22\",\"expiryDate\":\"2016-08-23\",\"excerciseStartDate\":\"2016-08-12\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2016-08-12\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String LEGALENTITYNOTVALID = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"SELL\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-08-22\",\"legalEntity\":\"NCS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String VALUEDATEISBEFORETRADEDATE = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-08-10\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String VALUEDATEISISNONWORKINGDAY = "{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2017-01-01\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String MULTIPLEVALIDATORFAILEDNOTVALIDLEGALENTITYANDCOUNTRYPARTYNOTSUPPORTEDSINGLE = "{\"customer\":\"PLUTO3\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-09-01\",\"legalEntity\":\"NCS Zurich\",\"trader\":\"Johann Baumfiddler\"}";
	private static final String MULTIPLEVALIDATORFAILEDNOTVALIDLEGALENTITYANDCOUNTRYPARTYNOTSUPPORTEDBULK = "[{\"customer\":\"PLUTO3\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-09-01\",\"legalEntity\":\"CS Zurich\",\"trader\":\"Johann Baumfiddler\"},{\"customer\":\"PLUTO1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2016-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2016-09-01\",\"legalEntity\":\"NCS Zurich\",\"trader\":\"Johann Baumfiddler\"}]";


	@Autowired
    private ApplicationContext context;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        Application.getManager().addListener(americanExerciseDateValidator);
        Application.getManager().addListener(counterpartySupportedValidator);
        Application.getManager().addListener(currenciesISO4217Validator);
        Application.getManager().addListener(expiryAndPremiumDateBeforeDeliveryDateValidator);
        Application.getManager().addListener(legalEntityValidator);
        Application.getManager().addListener(valueDateCannotBeBeforeTradeDateValidator);
        Application.getManager().addListener(valueDateCannotFallNonWorkingDayValidator);
    }
    
    
    @Test
    public void emptyTradeDataPassedToAPI() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(""))
        		.andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test
    public void noErrorEverythingIsOkSingle() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(SINGLENOERROR))
        		.andDo(print())
        		.andExpect(status().isOk());
    }
    
    @Test
    public void noErrorEverythingIsOkBulk() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEBULKURL).contentType(MediaType.APPLICATION_JSON).content(BULKNOERROR))
        		.andDo(print())
        		.andExpect(status().isOk());
    }

    private ResultMatcher getJsonPath(String message) {
		return getJsonPath(0, message);
	}
    
	private ResultMatcher getJsonPath(int index, String message) {
		return jsonPath("$.messages[" + index + "]").value(message);
	}
    
    @Test
    public void americanExerciseFail() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(AMERICANEXERCISEDATE))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(americanExerciseDateValidator.getMessage()));
    }
    
    @Test
    public void countrypartyNotSupported() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(COUNTRYPARTYNOTSUPPORTED))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(counterpartySupportedValidator.getMessage()));
    }
    
    @Test
    public void currenciesNotValidISO4217() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(CURRENCIESNOTVALIDISO4217))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(currenciesISO4217Validator.getMessage()));
    }
    
    @Test
    public void expiryAndPremiumDateNotBeforeDeliveryDate() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(EXPIRYANDPREMIUMDATENOTBEFOREDELIVERYDATE))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(expiryAndPremiumDateBeforeDeliveryDateValidator.getMessage()));
    }
    
    @Test
    public void LegalEntityNotValid() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(LEGALENTITYNOTVALID))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(legalEntityValidator.getMessage()));
    
    }
    
    @Test
    public void valueDateIsBeforeTradeDate() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(VALUEDATEISBEFORETRADEDATE))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(valueDateCannotBeBeforeTradeDateValidator.getMessage()));
    }
    
    @Test
    public void valueDateIsIsNonWorkingDay() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(VALUEDATEISISNONWORKINGDAY))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(valueDateCannotFallNonWorkingDayValidator.getMessage()));
    }
    
    @Test
    public void multipleValidatorFailedNotValidLegalEntityAndCountrypartyNotSupportedSingle() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEURL).contentType(MediaType.APPLICATION_JSON).content(MULTIPLEVALIDATORFAILEDNOTVALIDLEGALENTITYANDCOUNTRYPARTYNOTSUPPORTEDSINGLE))
        		.andDo(print())
        		.andExpect(status().isNotAcceptable())
        		.andExpect(getJsonPath(counterpartySupportedValidator.getMessage()))
        		.andExpect(getJsonPath(1, legalEntityValidator.getMessage()));
    }
    
    @Test
    public void multipleValidatorFailedNotValidLegalEntityAndCountrypartyNotSupportedBulk() throws Exception {
        this.mockMvc.perform(
        		post(VALIDATEBULKURL).contentType(MediaType.APPLICATION_JSON).content(MULTIPLEVALIDATORFAILEDNOTVALIDLEGALENTITYANDCOUNTRYPARTYNOTSUPPORTEDBULK))
        		.andDo(print())
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.[0]messages[0]").value(counterpartySupportedValidator.getMessage()))
        		.andExpect(jsonPath("$.[1]messages[0]").value(legalEntityValidator.getMessage()));
    }
}
