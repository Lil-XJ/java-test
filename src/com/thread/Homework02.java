package com.thread;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/27 11:35
 */
public class Homework02 {
    public static void main(String[] args) {
        R1 r1 = new R1();
        Thread t1 = new Thread(r1);
        t1.setName("t1");
        Thread t2 = new Thread(r1);
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}
class R1 implements Runnable{
    private int total = 10000;
    @Override
    public void run() {
        while(true){
            synchronized (this){
                if(total < 1000){
                    System.out.println("总票数小于1000");
                    break;
                }
                total -= 1000;
                System.out.println(Thread.currentThread().getName() + "取出1000,当前余额" + total);
            }

            try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
