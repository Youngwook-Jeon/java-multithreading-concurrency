package threadCoordination;

import java.math.BigInteger;

public class ThreadInterrupt2 {

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("100000000")));
        thread.setDaemon(true); // 메인 쓰레드가 종료될 시 데몬쓰레드는 강제적으로 자동 종료되게 함
        thread.start();
//        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
