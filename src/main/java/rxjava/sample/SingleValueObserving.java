package rxjava.sample;

import rx.Observable;
import rxjava.sample.work.Work;

public class SingleValueObserving {

	
	public static void main(String[] args) {
		
		Observable<String> someString  = Observable.just("value");
		
		
		someString.subscribe(v -> Work.longComputation("task1 " + v, () -> v));
		System.out.println("----");
		someString.subscribe(v -> Work.longComputation("task2 " + v, () -> v));

		Work.waitComputation(2);
	}
}
