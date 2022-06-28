package threadcreation;

import java.util.List;

public class Practice1 {

    // Add any necessary member variables here
    List<Runnable> tasks;
    /*
     * @param tasks to executed concurrently
     */
    public Practice1(List<Runnable> tasks) {
        // Complete your code here
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        // complete your code here
        tasks.forEach(runnable -> new Thread(runnable).start());
    }
}
