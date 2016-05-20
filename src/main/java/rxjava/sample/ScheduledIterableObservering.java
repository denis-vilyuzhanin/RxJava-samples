package rxjava.sample;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rxjava.sample.work.Work;

public class ScheduledIterableObservering {

	public static void main(String[] args) throws InterruptedException {
		List<String> values = Arrays.asList("value1", "value2");
		
		Observable<String> observableValues = Observable.from(values).subscribeOn(Schedulers.newThread());
		
		observableValues.subscribe(v -> Work.longComputation("task1 " + v, () -> v));
		observableValues.subscribe(v -> Work.longComputation("task2 " + v, () -> v));
		System.out.println("=======");
		Work.waitComputation(4);
	}
}
