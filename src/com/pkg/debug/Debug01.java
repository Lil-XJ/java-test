package com.pkg.debug;

import java.util.Arrays;

public class Debug01 {
    public static void main(String[] args) {
      /*  int sum = 0;
        for (int i = 0; i < 5; i++) {
            sum += i;
            System.out.println("i=" + i);
            System.out.println("sum=" + sum);
        }*/
        int [] arr = {1,3,2,5,4};
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
