package interthreadcommunication;

public class Synchronization {

    public static int counter1 = 0;
    public static int counter2 = 0;
    public static int counter3 = 0;

    // We have to make sure this method is called only by a single thread at a given time
    public static synchronized void increment1() {
        counter1++;
    }

    // Because our Synchronization object has a single lock:
    // this is why the methods can not be executed at the same time
    public static synchronized void increment2() {
        counter2++;
    }

    public static void increment3() {
        // class level locking
        // rule of thumb: we synchronize blocks that are 100% necessary
        synchronized (Synchronization.class) {
            counter3++;
        }
    }

    public static void process() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment1();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment2();
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

        System.out.println("The counter1 is: " + counter1);
        System.out.println("The counter2 is: " + counter2);
    }

    public static void main(String[] args) {
        process();
    }
}
