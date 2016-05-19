package rxjava.sample;

import rx.Observable;

public class SingleValueObserving {

	
	public static void main(String[] args) {
		
		Observable<String> someString  = Observable.just("value");
		
		
		someString.subscribe(s -> System.out.println(s), 
				             e->e.printStackTrace(), 
				             () -> System.out.println("Done 1"));
		System.out.println("----");
		someString.subscribe(s -> System.out.println(s), 
	             e->e.printStackTrace(), 
	             () -> System.out.println("Done 2"));
	}
}
