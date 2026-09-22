package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 11:23
 */
public class Homework02 {
    public static void main(String[] args) {
        System.out.println(Frock.getNextNum());
        System.out.println(Frock.getNextNum());
        Frock frock = new Frock();
        System.out.println(frock.getSerialNumber());
        Frock frock2 = new Frock();
        System.out.println(frock2.getSerialNumber());
        Frock frock3 = new Frock();
        System.out.println(frock3.getSerialNumber());
    }
}
class Frock {
    private static int currentNum = 100000;
    private int serialNumber;
    public static int getNextNum(){
        return currentNum += 100;
    }
    public int getSerialNumber(){
        return serialNumber;
    }
    public Frock(){
        serialNumber = getNextNum();
    }
}
