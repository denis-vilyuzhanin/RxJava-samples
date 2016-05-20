package rxjava.sample;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class IntervalObserving {

	public static void main(String[] args) throws InterruptedException {
		
		Observable<Long> sequence = Observable.interval(1, TimeUnit.SECONDS);
		
		sequence.subscribe(t -> System.out.println("1: " + t));
		
		Thread.sleep(3000);
		
		sequence.subscribe(t -> System.out.println("2: " + t));
		
		Thread.sleep(10000);
	}
}
