package threadswithexecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Work(i + 1));
        }

        // we prevent the executor to execute any further jobs
        executorService.shutdown();

        // terminate actual jobs
        try {
            if (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    static class Work implements Runnable {

        private int id;

        Work(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Task with id " + id + " is in work - thread id: " +
                    Thread.currentThread().getId());
            long duration = (long) (Math.random() * 5);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
