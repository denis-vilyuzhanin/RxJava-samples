package rxjava.sample;

import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class CurrencyExchangeObserving {

	static double previousRate = 0;
	
	public static void main(String[] args) throws InterruptedException {
		CurrencyExchangeService exchangeService = new CurrencyExchangeService("UAH", "USD");
		exchangeService.start();
		
		Observable<ExchangeRate> rateOriginal = exchangeService.currentExchagneRate();
		Observable<ExchangeRate> rate = rateOriginal.map(r -> {
			double newRate = r.getRate();
			if (previousRate > r.getRate()) {
				r.setRate(newRate * -1);
			} 
			previousRate = newRate;
			return r;
		});
		
		rate.subscribe(currentRate -> System.out.println("1: " + currentRate));
		Thread.sleep(2000);
		rate.subscribe(currentRate -> System.out.println("2: " + currentRate),
					   e -> e.printStackTrace(),
					   () -> System.out.println("2: Done"));
		
		rate.map(r -> "3: " + r.toString()).subscribe(System.out::println);
		
		
		
		System.out.println("Running ...");
		Thread.sleep(20000);
		exchangeService.interrupt();
	}
	
	
	static class CurrencyExchangeService extends Thread {
		
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
	
	
	static class ExchangeRate {
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
}
