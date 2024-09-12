package com.nmdsky.deadlockthread;

public class SDeadLock implements Runnable {
    public static void main(String[] args) {
        SDeadLock dl1 = new SDeadLock();
        SDeadLock dl2 = new SDeadLock();
        dl1.flag = 1;
        dl2.flag = 0;
        new Thread(dl1).start();
        new Thread(dl2).start();
    }

    public int flag = 1;
    static Object o1 = new Object();
    static Object o2 = new Object();

    @Override
    public void run() {
        System.out.println("flag:" + flag);
        //当flag==1，锁住o1
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //想把o2锁住然后打印信息
            synchronized (o2) {
                System.out.println("when flag==1, o1 and o2 has been locked");
            }
        }
        //当flag==2，锁住o2
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            //想把o1锁住然后打印信息
            synchronized (o1) {
                System.out.println("when flag==2, o1 and o2 has been locked");
            }

        }
    }
}
