package rxjava.sample.work;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Work {

	public static long LONG_COMPUTATION_TIME = 1000;
	
	private static AtomicInteger computedValuesCounter = new AtomicInteger(); 
	
	public static <R> R longComputation(String name, Callable<R> computation) {
		
		System.out.println(name + ": Computation starting ...");
		try {
			Thread.sleep(LONG_COMPUTATION_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		R result;
		try {
			result = computation.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println(name + ": Computation done");
		computedValuesCounter.incrementAndGet();
		return result;
	}
	
	public static void waitComputation(int expected) {
		try {
			while(computedValuesCounter.get() < expected) {
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			
		}
		System.out.println("All computation completed");	
	}
}
