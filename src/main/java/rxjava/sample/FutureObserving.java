package rxjava.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import rx.Observable;

public class FutureObserving {

	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		Future<String> result = executor.submit(() -> {
			Thread.sleep(100);
			return "computed string";
		});
		
		Observable<String> stringComputing = Observable.from(result);
		
		stringComputing.subscribe(v -> System.out.println("1: " + v), 
                (e) -> e.printStackTrace(), 
                () -> System.out.println("Done"));

		stringComputing.subscribe(v -> System.out.println("2: " + v));

		
		
		
		Thread.sleep(200);
		
		executor.shutdown();
	}
}
