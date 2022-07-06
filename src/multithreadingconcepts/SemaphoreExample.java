package multithreadingconcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 12; i++) {
            executorService.execute(Downloader.INSTANCE::download);
        }
    }

    enum Downloader {
        INSTANCE;

        private Semaphore semaphore = new Semaphore(3, true);

        public void download() {
            try {
                semaphore.acquire();
                downloadData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }

        private void downloadData() {
            try {
                System.out.println("Downloading data from the web...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
