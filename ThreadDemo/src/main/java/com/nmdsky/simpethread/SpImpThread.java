package com.nmdsky.simpethread;

public class SpImpThread implements Runnable{
    public static void main(String[] args) {
        System.out.println("----------------main start-------------");
        SpImpThread s = new SpImpThread();
        Thread t = new Thread(s);
        t.start();
        System.out.println("----------------main end-------------");
    }
    @Override
    public void run() {
        System.out.println("fuck your implements Runnable way");
        System.out.println("fuck your implements Runnable way2222");
    }
}
