package rxjava.sample.services;

import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Observer;

public class CurrencyExchangeService extends Thread {
	
	private ConcurrentHashMap<Observer<? super ExchangeRate>, Object> observers = new ConcurrentHashMap<>();
	
	private String currencyFrom;
	private String currencyTo;
	private double exchangeRate;
	
	public CurrencyExchangeService(String currencyFrom, String currencyTo) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
	}



	@Override
	public void run() {
		
		try {
			while(!Thread.interrupted()) {
				exchangeRate = 
						(int)(Math.abs(exchangeRate + 0.5 - Math.random()) * 100)/ 100.0;
				
				ExchangeRate currentRate = new ExchangeRate();
				currentRate.setCurrencyFrom(currencyFrom);
				currentRate.setCurrencyTo(currencyTo);
				currentRate.setRate(exchangeRate);
				
				observers.keySet().forEach(observer -> {
					observer.onNext(currentRate);
				});
				
				Thread.sleep(1000);
			}
		} catch (InterruptedException e){
			
		} catch (Exception e) {
			observers.keySet().forEach(observer -> observer.onError(e));
		}
		observers.keySet().forEach(observer -> observer.onCompleted());
	}
	
	public Observable<ExchangeRate> currentExchagneRate() {
		return Observable.create(s -> {
			observers.put(s, "");
		});
	}
	
}