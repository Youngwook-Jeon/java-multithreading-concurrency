package concurrentcollections;

import java.util.Random;
import java.util.concurrent.*;

// multiple threads can wait for each other.
// it can be reused over and over again.
public class CyclicBarrierExample {

    static class Worker implements Runnable {

        private int id;
        private Random random;
        private CyclicBarrier cyclicBarrier;

        public Worker(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.random = new Random();
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            doWork();
        }

        private void doWork() {
            try {
                System.out.println("Thread with Id " + this.id + " starts working...");
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                // when all the threads call await() this is when the barrier is broken!
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("All tasks have been finished...");
        });

        for (int i = 0; i < 5; i++) {
            service.execute(new Worker(i + 1, cyclicBarrier));
        }

        service.shutdown();
    }
}
