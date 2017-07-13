package recep.demirkoparan.trade.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
*
* @author recepd
*/
public class Trade {

	private String customer;//"PLUTO1", 
	private String ccyPair;//"EURUSD"
	private TradeType type;//Spot,Forward,VanillaOption
	private TradeDirection direction;//BUY,SELL
	private Date tradeDate;//"2016-08-11"
	private BigDecimal amount1;//1000000.00
	private BigDecimal amount2;//1120000.00
	private BigDecimal rate;//1.12
	private Date valueDate;//"2016-08-15"
	//valid for VanillaOption
	private TradeStyle style;//EUROPEAN,AMERICAN
	private TradeStrategy strategy;//CALL
	private Date deliveryDate;//"2016-08-22"
	private Date expiryDate;//"2016-08-19"
	private Date excerciseStartDate;//"2016-08-10"
	private String payCcy;//"USD"
	private BigDecimal premium;//0.20
	private String premiumCcy;//"USD"
	private String premiumType;//"%USD"
	private Date premiumDate;//"2016-08-12"
	
	private String legalEntity;//"CS Zurich"
	private String trader;//"Johann	Baumfiddler"
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCcyPair() {
		return ccyPair;
	}
	public void setCcyPair(String ccyPair) {
		this.ccyPair = ccyPair;
	}
	public TradeType getType() {
		return type;
	}
	public void setType(TradeType type) {
		this.type = type;
	}
	public TradeDirection getDirection() {
		return direction;
	}
	public void setDirection(TradeDirection direction) {
		this.direction = direction;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	public BigDecimal getAmount1() {
		return amount1;
	}
	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}
	public BigDecimal getAmount2() {
		return amount2;
	}
	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public TradeStyle getStyle() {
		return style;
	}
	public void setStyle(TradeStyle style) {
		this.style = style;
	}
	public TradeStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(TradeStrategy strategy) {
		this.strategy = strategy;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getExcerciseStartDate() {
		return excerciseStartDate;
	}
	public void setExcerciseStartDate(Date excerciseStartDate) {
		this.excerciseStartDate = excerciseStartDate;
	}
	public String getPayCcy() {
		return payCcy;
	}
	public void setPayCcy(String payCcy) {
		this.payCcy = payCcy;
	}
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	public String getPremiumCcy() {
		return premiumCcy;
	}
	public void setPremiumCcy(String premiumCcy) {
		this.premiumCcy = premiumCcy;
	}
	public String getPremiumType() {
		return premiumType;
	}
	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}
	public Date getPremiumDate() {
		return premiumDate;
	}
	public void setPremiumDate(Date premiumDate) {
		this.premiumDate = premiumDate;
	}
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	public String getTrader() {
		return trader;
	}
	public void setTrader(String trader) {
		this.trader = trader;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Trade [customer=");
		builder.append(customer);
		builder.append(", ccyPair=");
		builder.append(ccyPair);
		builder.append(", type=");
		builder.append(type);
		builder.append(", direction=");
		builder.append(direction);
		builder.append(", tradeDate=");
		builder.append(tradeDate);
		builder.append(", amount1=");
		builder.append(amount1);
		builder.append(", amount2=");
		builder.append(amount2);
		builder.append(", rate=");
		builder.append(rate);
		builder.append(", valueDate=");
		builder.append(valueDate);
		builder.append(", style=");
		builder.append(style);
		builder.append(", strategy=");
		builder.append(strategy);
		builder.append(", deliveryDate=");
		builder.append(deliveryDate);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", excerciseStartDate=");
		builder.append(excerciseStartDate);
		builder.append(", payCcy=");
		builder.append(payCcy);
		builder.append(", premium=");
		builder.append(premium);
		builder.append(", premiumCcy=");
		builder.append(premiumCcy);
		builder.append(", premiumType=");
		builder.append(premiumType);
		builder.append(", premiumDate=");
		builder.append(premiumDate);
		builder.append(", legalEntity=");
		builder.append(legalEntity);
		builder.append(", trader=");
		builder.append(trader);
		builder.append("]");
		return builder.toString();
	}

	
}
