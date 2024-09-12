package com.nmdsky.threadpools;

import java.util.concurrent.*;

/**
 * 动态调参实现动态线程池
 * 这里的注释效果相同
 */
public class DPoolsThread {
    public static void main(String[] args) {
        int min = 4;
        int max = 10;
        ExecutorService est=Executors.newCachedThreadPool();
//        ExecutorService es = new ThreadPoolExecutor(min, max,
//                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
//        for (int i = 0; i < 6; i++) {
//            es.submit(new Task("" + i));
//        }
//        es.shutdown();
        System.out.println("=============");
        for (int i = 0; i < 6; i++) {
            est.submit(new Task("" + i));
        }
        est.shutdown();
    }
}
