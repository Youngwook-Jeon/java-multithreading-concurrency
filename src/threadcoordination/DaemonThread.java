package threadcoordination;

public class DaemonThread {

    public static void main(String[] args) {
        Thread t1 = new Thread(new DaemonWorker());
        Thread t2 = new Thread(new NormalWorker());

        t1.setDaemon(true);
        t1.start();
        t2.start();

        System.out.println("Main thread is finished!");
    }

    private static class DaemonWorker implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Daemon thread is running...");
            }
        }
    }

    private static class NormalWorker implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Normal thread is finished...");
        }
    }
}
