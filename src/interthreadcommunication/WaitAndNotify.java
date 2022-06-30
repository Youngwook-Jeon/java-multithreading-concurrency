package interthreadcommunication;

public class WaitAndNotify {

    public static void main(String[] args) {
        Process process = new Process();
        Thread t1 = new Thread(() -> {
            try {
                process.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }

    public static class Process {
        public void produce() throws InterruptedException {
            synchronized (this) {
                System.out.println("Running the produce method...");
                // the thread releases the lock on the object until other thread call notify()
                wait();
                System.out.println("Again in the producer method...");
            }
        }

        public void consume() throws InterruptedException {
            Thread.sleep(1000);
            synchronized (this) {
                System.out.println("Consume method is executed...");
                notify();
                // it is not going to handle the lock: we can make further operations
                Thread.sleep(5000);
            }
        }
    }
}
