package concurrentcollections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Latch {

    static class Worker implements Runnable {

        private int id;
        private CountDownLatch latch;

        Worker(int id, CountDownLatch countDownLatch) {
            this.id = id;
            this.latch = countDownLatch;
        }

        @Override
        public void run() {
            doWork();
            latch.countDown();
        }

        private void doWork() {
            try {
                System.out.println("Thread with Id " + this.id + " starts working...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            service.execute(new Worker(i, countDownLatch));
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks have been finished!");
        service.shutdown();
    }
}
