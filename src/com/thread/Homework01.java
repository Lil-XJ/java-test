package com.thread;

import java.util.Scanner;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/27 10:59
 */
public class Homework01 {
    public static void main(String[] args) {
        T1 t1 = new T1();
        t1.start();
        T3 t3 = new T3(t1);
        t3.start();
    }
}
class T1 extends Thread{
    public boolean loop = true;
    @Override
    public void run() {
        while (loop){
            System.out.println((int)(Math.random() * 100));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class T3 extends Thread{
    private T1 t1;
    private Scanner scanner = new Scanner(System.in);
    public T3 (T1 t1){
        this.t1 = t1;
    }
   /* synchronized (t1){

    }*/
    @Override
    public void run() {
        while (true){
            if(scanner.next().charAt(0) == 'q'){
                t1.loop = false;
                break;
            }
        }
    }
}
