package com.thread;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/26 15:22
 */
public class ThreadMethod {
    public static void main(String[] args) throws InterruptedException {
        T2 t2 = new T2();
        t2.start();
        for (int i = 1; i <= 20; i++) {
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName() + "正在运行：" + i);
            if(i == 5){
                System.out.println("main线程让步，让t2线程运行");
//                t2.join();
                Thread.yield();
            }
        }
    }
}
class T2 extends Thread {
    @Override
    public void run(){
        for (int i = 1; i <= 20; i++) {
            try {
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(T2.currentThread().getName() + "正在运行：" + i);
        }
    }
}
