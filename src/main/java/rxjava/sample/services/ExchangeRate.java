package rxjava.sample.services;

public class ExchangeRate {
	private String currencyFrom;
	private String currencyTo;
	private double rate;
	
	@Override
	public String toString() {
		return rate + currencyFrom + "/" + currencyTo;
	}
	
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}
	public String getCurrencyTo() {
		return currencyTo;
	}
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}