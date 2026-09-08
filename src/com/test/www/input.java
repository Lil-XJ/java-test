package com.test.www;

import java.util.Scanner;

public class input {
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        System.out.println("请输入名字：");
        String name = scanner.next();
        System.out.println("你的名字" + name);
    }
}
