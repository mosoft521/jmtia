package io.github.viscent.mtia.ch12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ImplicitControlThreadsCount2 {
    final ExecutorService executorSerivice = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(3);

    public void doSomething(final String data) throws InterruptedException {
        semaphore.acquire();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    process(data);
                } finally {
                    semaphore.release();
                }
            }
        };

        executorSerivice.submit(task);
    }

    private void process(String data) {
        // ......
        System.out.println(">>>" + data + "<<<" + semaphore.availablePermits() + ">>>" + semaphore.getQueueLength());
    }


    public static void main(String[] args) {
        ImplicitControlThreadsCount2 implicitControlThreadsCount = new ImplicitControlThreadsCount2();
        try {
            implicitControlThreadsCount.doSomething("01");
            implicitControlThreadsCount.doSomething("02");
            implicitControlThreadsCount.doSomething("03");
            implicitControlThreadsCount.doSomething("04");
            implicitControlThreadsCount.doSomething("05");
            implicitControlThreadsCount.doSomething("06");
            implicitControlThreadsCount.doSomething("07");
            implicitControlThreadsCount.doSomething("08");
            implicitControlThreadsCount.doSomething("09");
            for (int i = 10; i < 100; i++) {
                implicitControlThreadsCount.doSomething(Integer.toString(i));
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("end");
    }
}
