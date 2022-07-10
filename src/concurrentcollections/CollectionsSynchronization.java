package concurrentcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsSynchronization {

    public static void main(String[] args) {
        // add() and remove() methods are synchronized
        // intrinsic lock - not that efficient because threads
        // have to wait for each other even when they want to execute
        // independent methods
        List<Integer> integers = Collections.synchronizedList(new ArrayList<>());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                integers.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                integers.add(i);
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

        System.out.println("Size of array: " + integers.size());
    }
}
