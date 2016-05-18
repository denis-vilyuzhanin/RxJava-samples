package rxjava.sample;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class IterableObservering {

	public static void main(String[] args) {
		List<String> values = Arrays.asList("value1", "value2");
		
		Observable<String> observableValues = Observable.from(values);
		
		observableValues.subscribe(v -> System.out.println("1: " + v), 
				                  (e) -> e.printStackTrace(), 
				                  () -> System.out.println("Done"));
		
		observableValues.subscribe(v -> System.out.println("2: " + v));
		
		
		
	}
}
