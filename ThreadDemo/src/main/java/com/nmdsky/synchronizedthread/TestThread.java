package com.nmdsky.synchronizedthread;

public class TestThread implements Runnable {

    public static void main(String[] args) {
        TestThread t1 = new TestThread();
        new Thread(t1, "售票员A").start();
        new Thread(t1, "售票员B").start();
        new Thread(t1, "售票员C").start();
    }

    private int tickets = 5;

    @Override
    public void run() {

        for (int i = 0; i < 50; i++) {
            synchronized (this) {
                if (tickets > 0) {
                    //在此加入线程休眠为了让其他的线程来争抢cpu资源，会出现不安全线程现象
                    //睡眠的目的是线程切换，更易发生争抢
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖票,ticket= " + tickets--);
                }
            }
        }

    }
}