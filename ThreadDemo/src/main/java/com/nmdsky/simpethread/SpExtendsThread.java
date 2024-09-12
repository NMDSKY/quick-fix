package com.nmdsky.simpethread;

public class SpExtendsThread extends Thread{
    public static void main(String[] args) {
        Thread t = new SpExtendsThread();
        t.start();
    }

    @Override
    public void run() {
        System.out.println("fuck your extends");
    }
}
