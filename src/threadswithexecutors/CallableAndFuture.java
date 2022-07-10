package threadswithexecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<String>> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Future<String> stringFuture = executorService.submit(new Processor(i + 1));
            list.add(stringFuture);
        }

        for (Future<String> f : list) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class Processor implements Callable<String> {

        private int id;

        public Processor(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            return "Id: " + id;
        }
    }
}
