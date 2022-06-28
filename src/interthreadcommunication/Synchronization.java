package interthreadcommunication;

public class Synchronization {

    public static int counter = 0;

    // We have to make sure this method is called only by a single thread at a given time
    public static synchronized void increment() {
        counter++;
    }

    public static void process() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The counter is: " + counter);
    }

    public static void main(String[] args) {
        process();
    }
}
