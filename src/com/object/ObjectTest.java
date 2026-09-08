package com.object;

import java.util.Scanner;

public class ObjectTest {
    public static void main(String[] args) {
        class Cat {
            public void cal01(int n){
                int sum = 0;
                for (int i = 1;i <= n;i++){
                    sum += i;
                }
                System.out.println(sum);
            }
            public int getSum(int num1,int num2){
                return num1 + num2;
            }
        }
        Cat cat1 = new Cat();
//        cat1.cal01(1000);
        Scanner scan = new Scanner(System.in);
        int num1 = Integer.parseInt(scan.next());
        int num2 = Integer.parseInt(scan.next());
        System.out.println(cat1.getSum(num1,num2));
    }
}
