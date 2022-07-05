package multithreadingconcepts;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLockExample {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        LiveLockExample liveLock = new LiveLockExample();
        new Thread(liveLock::worker1, "worker1").start();
        new Thread(liveLock::worker2, "worker2").start();
    }

    public void worker1() {
        while (true) {
            try {
                lock1.tryLock(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Worker1 acquires the lock1");
            System.out.println("Worker1 tries to get lock2");

            if (lock2.tryLock()) {
                System.out.println("Worker1 acquires the lock2...");
            } else {
                System.out.println("Worker1 can not acquire lock2. Release lock1.");
                lock1.unlock();
                continue;
            }

            System.out.println("Remaining worker1's logic");
            break;
        }

        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        while (true) {
            try {
                lock2.tryLock(50, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Worker2 acquires the lock2");
            System.out.println("Worker2 tries to get lock1");

            if (lock1.tryLock()) {
                System.out.println("Worker2 acquires the lock1...");
            } else {
                System.out.println("Worker2 can not acquire lock1. Release lock2");
                lock2.unlock();
                continue;
            }

            System.out.println("Remaining worker2's logic");
            break;
        }

        lock1.unlock();
        lock2.unlock();
    }
}
