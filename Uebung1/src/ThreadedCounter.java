public class ThreadedCounter implements Runnable {

	public int anzahlForThisThread;
	final private int min, max;
	public long elapsedTimeForThisThread;
	
	/**
	 * Must be called before to start the thread.
	 * It is needed to let this thread know the range in which it should do its computation (see run()).
	 * @param minp is the minimum of the calculated range
	 * @param maxp is the maximum of the range
	 */
	public ThreadedCounter(int minp, int maxp) {
		this.min = minp;
		this.max = maxp;
	}
	
	/**
	 * This is the core of this class.
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
