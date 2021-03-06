package rxjava.sample;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rxjava.sample.work.Work;

public class IterableObservering {

	public static void main(String[] args) throws InterruptedException {
		List<String> values = Arrays.asList("value1", "value2");
		
		Observable<String> observableValues = Observable.from(values);
		
		observableValues.subscribe(v -> Work.longComputation("task1 " + v, () -> v));
		observableValues.subscribe(v -> Work.longComputation("task2 " + v, () -> v));
		
		Work.waitComputation(4);
	}
}
