package com.thread;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/26 16:41
 */
public class ThreadExercise {
    public static void main(String[] args) throws InterruptedException {
        H1 h1 = new H1();
        Thread thread = new Thread(h1);
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + "Hi" + i);
            Thread.sleep(1000);
            if(i == 5){
                thread.start();
                thread.join();
            }
        }
    }
}
class H1 implements Runnable{
    @Override
    public void run(){
        for (int i = 1; i <= 10 ; i++) {
            System.out.println(Thread.currentThread().getName() + "Hello" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
