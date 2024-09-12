package com.nmdsky.simpethread;

public class SpThread {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            //lam表达式重写run方法
            System.out.println("fuck your mother");
        });
        Thread tp = new Thread(()->{
            //lam表达式重写run方法
            System.out.println("fuck your father");
        });
        tp.start();
        t.start();

    }
}
