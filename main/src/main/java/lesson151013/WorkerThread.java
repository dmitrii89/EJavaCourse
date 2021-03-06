package lesson151013;

import lesson151008.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by dmitr on 04.10.2017.
 */
public class WorkerThread{

    private Thread thread;
    private Queue<Runnable> tasks = new LinkedList<>();

    private final class TaskRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            // ignore
                        }
                    }
                    task = tasks.poll();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }

    public WorkerThread() {
        thread = new Thread(new TaskRunner());
        thread.start();
    }


    private void execute(Runnable runnable) {
        synchronized (tasks) {
            tasks.offer(runnable);
            tasks.notify();
        }
    }

    public static void main(String[] args) {
        System.out.println("Start");
        WorkerThread worker = new WorkerThread();
        Utils.pause(3000);
        worker.execute(new Runnable(){
            @Override
            public void run() {
                System.out.println("hello from " + Thread.currentThread().getName());
            }
        });
        Utils.pause(3000);
        worker.execute(new Runnable(){
            @Override
            public void run() {
                System.out.println("another hello from " + Thread.currentThread().getName());
            }
        });
    }
}
