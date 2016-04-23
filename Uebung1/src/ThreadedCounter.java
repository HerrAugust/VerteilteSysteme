public class ThreadedCounter implements Runnable {

	public int anzahlForThisThread;
	final private int min, max;
	public long elapsedTimeForThisThread;
	
	/**
	 * Must be called before to start the thread
	 * @param r
	 */
	public ThreadedCounter(int minp, int maxp) {
		this.min = minp;
		this.max = maxp;
	}
	
	/**
	 * We count how many prime numbers there are between [i; j] (arguments passed by AtomicReference)
	 */
	@Override
	public void run() {
		if(min <= 0 || max <= 0 || max < min)
		{
			System.err.println("You must enter correct values before to start the thread!.");
			System.err.println("max = " + max + "; min = " + min);
			return;
		}
		
		long startTime = System.currentTimeMillis();
        for(int i = min; i <= max; i++) {
            if(Teil3.isPrime(i)) {
                this.anzahlForThisThread++;
            }
        }
        long stopTime = System.currentTimeMillis();
        this.elapsedTimeForThisThread = stopTime - startTime;
        
        System.out.println("Thread [" + this.min + "; " + this.max + "] finished");
	}

}
