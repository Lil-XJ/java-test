package com.test.www;

import java.util.Arrays;
import java.util.Scanner;

public class YangHui {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int num = Integer.parseInt(scan.next());
        int arr[][] = new int[num][];
        for(int i = 0;i < arr.length;i++){
            arr[i] = new int[i + 1];
            for (int j = 0;j < arr[i].length;j++){
                if(j == 0 || j == i) {
                    arr[i][j] = 1;
                }else{
                    arr[i][j] = arr[i - 1][j] + arr[i-1][j-1];
                }
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print(Arrays.deepToString(arr));

    }
}
