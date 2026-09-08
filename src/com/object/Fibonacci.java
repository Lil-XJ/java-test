package com.object;

public class Fibonacci {
    public static void main(String[] args) {
        class Fibo {
            public int getNum(int n){
                if(n > 2){
                   return getNum(n - 1) + getNum(n - 2);
                }else{
                    return 1;
                }
            }
        }
        Fibo fibo = new Fibo();
        System.out.println(fibo.getNum(0));
    }
}
