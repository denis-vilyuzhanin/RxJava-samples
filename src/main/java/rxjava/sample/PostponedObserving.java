package rxjava.sample;


import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.subjects.PublishSubject;

public class PostponedObserving {

	public static void main(String[] args) {
		
		PublishSubject<String> computedValues = PublishSubject.create();

		computedValues.subscribe(v -> System.out.println("1: " + v), 
                (e) -> e.printStackTrace(), 
                () -> System.out.println("Done"));

		computedValues.subscribe(v -> System.out.println("2: " + v));
		
		AtomicInteger count = new AtomicInteger();
		Observable<String> mappedString = computedValues.map(v -> "[" + v + "-" + count.incrementAndGet() + "]").cache();
														
														
		mappedString.subscribe(v -> System.out.println("mapped1: " + v));
		mappedString.subscribe(v -> System.out.println("mapped2: " + v));
		
		computedValues.onNext("value1");
		computedValues.onNext("value2");
		computedValues.onNext("value3");
		
		computedValues.subscribe(v -> System.out.println("3: " + v));
		
		computedValues.onNext("value4");
		computedValues.onNext("value5");
		
		computedValues.onCompleted();
	}
}

