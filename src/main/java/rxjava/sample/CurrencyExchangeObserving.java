package rxjava.sample;

import rx.Observable;
import rx.observables.ConnectableObservable;
import rxjava.sample.services.CurrencyExchangeService;
import rxjava.sample.services.ExchangeRate;

public class CurrencyExchangeObserving {

	static double previousRate = 0;
	
	public static void main(String[] args) throws InterruptedException {
		CurrencyExchangeService exchangeService = new CurrencyExchangeService("UAH", "USD");
		exchangeService.start();
		
		Observable<ExchangeRate> rateOriginal = exchangeService.currentExchagneRate();
		
		Observable<ExchangeRate> rate;
		//ConnectableObservable<ExchangeRate> rate;
		rate = rateOriginal.map(r -> {
			double newRate = r.getRate();
			if (previousRate > r.getRate()) {
				r.setRate(newRate * -1);
			} 
			previousRate = newRate;
			return r;
		});//.publish();
		
		rate.subscribe(currentRate -> System.out.println("1: " + currentRate));
		Thread.sleep(2000);
		rate.subscribe(currentRate -> System.out.println("2: " + currentRate),
					   e -> e.printStackTrace(),
					   () -> System.out.println("2: Done"));
		
		rate.map(r -> "3: " + r.toString()).subscribe(System.out::println);
		
		
		
		System.out.println("Running ...");
		//rate.connect();
		Thread.sleep(20000);
		exchangeService.interrupt();
	}
}
