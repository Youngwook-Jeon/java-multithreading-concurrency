package concurrentcollections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

// DelayQueue keeps the elements internally until a certain delay has expired.
// An object can only be taken from the queue when its delay has expired.
// We cannot place null items in the queue.
// If no delay has expired, then there is no head element and poll() method will return null.
// size() return the count of both expired and unexpired items.
public class DelayQueueExample {

    public static void main(String[] args) {
        BlockingQueue<DelayWorker> blockingQueue = new DelayQueue<>();
        try {
            blockingQueue.put(new DelayWorker(2000, "This is the first message..."));
            blockingQueue.put(new DelayWorker(10000, "This is the second message..."));
            blockingQueue.put(new DelayWorker(4500, "This is the third message..."));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (!blockingQueue.isEmpty()) {
            try {
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class DelayWorker implements Delayed {

        private long duration;
        private String message;

        public DelayWorker(long duration, String message) {
            this.duration = System.currentTimeMillis() + duration;
            this.message = message;
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            if (duration < ((DelayWorker) other).getDuration()) {
                return -1;
            } else if (duration > ((DelayWorker) other).getDuration()) {
                return 1;
            }
            return 0;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "DelayWorker{" +
                    "duration=" + duration +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
