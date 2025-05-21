package MonteCarloPI;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class MonteCarloPi {

    static final long NUM_POINTS = 50_000_000L;
    static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {

        // Without Threads
        System.out.println("Single threaded calculation started: ");
        long startTime = System.nanoTime();
        double piWithoutThreads = estimatePiWithoutThreads(NUM_POINTS);
        long endTime = System.nanoTime();
        System.out.println("Monte Carlo Pi Approximation (single thread): " + piWithoutThreads);
        System.out.println("Time taken (single threads): " + (endTime - startTime) / 1_000_000 + " ms");

        // With Threads
        System.out.printf("Multi threaded calculation started: (your device has %d logical threads)\n",NUM_THREADS);
        startTime = System.nanoTime();
        double piWithThreads = estimatePiWithThreads(NUM_POINTS, NUM_THREADS);
        endTime = System.nanoTime();
        System.out.println("Monte Carlo Pi Approximation (Multi-threaded): " + piWithThreads);
        System.out.println("Time taken (Multi-threaded): " + (endTime - startTime) / 1_000_000 + " ms");

    }

    // Monte Carlo Pi Approximation without threads
    public static double estimatePiWithoutThreads(long numPoints)
    {
        long inCirclePointsCount = 0;
        double pi,x,y;
        for(long i = 0; i < numPoints; i++)
        {
            x = ThreadLocalRandom.current().nextDouble();
            y = ThreadLocalRandom.current().nextDouble();
            boolean isInCircle = ((x*x) + (y*y)) <= 1;
            if(isInCircle)
                inCirclePointsCount++;
        }
        pi = ((double) inCirclePointsCount / numPoints) * 4;
        return pi;
    }

    // Monte Carlo Pi Approximation with threads
    public static double estimatePiWithThreads(long numPoints, int numThreads) throws InterruptedException, ExecutionException
    {
        AtomicLong AtomicInCirclePointsCount = new AtomicLong();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        double pi;
        for(int i = 0; i < numThreads; i++) {
        executor.execute(() -> {
            for (long j = 0; j < numPoints / numThreads; j++) {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();
                boolean isInCircle = ((x * x) + (y * y)) <= 1;
                if (isInCircle)
                    AtomicInCirclePointsCount.getAndIncrement();
            }
        });
    }
        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);

        pi = ((double) AtomicInCirclePointsCount.get() / numPoints) * 4;
        return pi;
    }
}