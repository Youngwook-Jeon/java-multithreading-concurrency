package concurrentcollections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// This is an interface that represents a queue that is thread safe
public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
        FirstWorker firstWorker = new FirstWorker(blockingQueue);
        SecondWorker secondWorker = new SecondWorker(blockingQueue);

        new Thread(firstWorker).start();
        new Thread(secondWorker).start();
    }

    static class FirstWorker implements Runnable {
        private BlockingQueue<Integer> blockingQueue;

        public FirstWorker(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            int counter = 0;
            while (true) {
                try {
                    blockingQueue.put(counter);
                    System.out.println("Putting item into the queue..." + counter);
                    counter++;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class SecondWorker implements Runnable {
        private BlockingQueue<Integer> blockingQueue;

        public SecondWorker(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Integer counter = blockingQueue.take();
                    System.out.println("Taking item from the queue..." + counter);
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
