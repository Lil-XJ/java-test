package com.test.www;

public class IntDetail {
    public static void main(String[] args) {
//        //java的整型常量默认为int型，声明long型常量须后加‘l’或‘L’
//        int n1 = 1; //4字节
//        long n2 = 1L;
//        //java的浮点型常量默认为double型；若要声明float型常量，需后加f或F
//        float f1 = 1.2f;
//        double f2 = 1.2;
//        System.out.println(n1 + " " + n2 + " " +f1);
//        System.out.printf("n1=%d, n2=%d, f1=%f, f2=%f", n1, n2, f1,f2);
//        double num = 8.1 / 3;
//        System.out.println(num);
//        //char 字符类型可以直接存放一个数字,但是输出是unicode编码对应的字符
//        char c1 = 98;
//        System.out.println(c1);
        int  n1 = (int)1.9;
        System.out.println(n1);
        int n2 = 2000;
        byte n3 = (byte)n2;
        System.out.println(n3);
    }
}
