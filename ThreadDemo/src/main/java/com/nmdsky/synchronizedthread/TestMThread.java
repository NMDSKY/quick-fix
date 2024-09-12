package com.nmdsky.synchronizedthread;

public class TestMThread implements Runnable {

    public static void main(String[] args) {
        TestMThread t1 = new TestMThread();
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
                    this.sale();
                }
            }
        }

    }

    public synchronized void sale() {  //同步方法
        if (tickets > 0) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "卖票,ticket= " + tickets--);
        }
    }

}