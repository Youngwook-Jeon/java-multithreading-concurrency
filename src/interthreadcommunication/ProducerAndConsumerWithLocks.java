package interthreadcommunication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumerWithLocks {

    public static void main(String[] args) {
        Worker worker = new Worker();
        Thread t1 = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }

    public static class Worker {

        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void produce() throws InterruptedException {
            lock.lock();
            System.out.println("Producer method...");
            condition.await();
            System.out.println("Again the Producer method...");
        }

        public void consume() throws InterruptedException {
            Thread.sleep(2000);
            lock.lock();
            System.out.println("Consumer method...");
            Thread.sleep(3000);
            condition.signal();
            lock.unlock();
        }
    }
}
