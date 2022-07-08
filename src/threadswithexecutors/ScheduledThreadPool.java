package threadswithexecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(
                new StockMarketUpdater(), 1000, 2000, TimeUnit.MILLISECONDS
        );
    }

    static class StockMarketUpdater implements Runnable {

        @Override
        public void run() {
            System.out.println("Updating and downloading stock related data from web...");
        }
    }
}
