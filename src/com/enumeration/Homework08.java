package com.enumeration;

import java.util.Scanner;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 17:48
 */
public class Homework08 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String color = scan.next();
        switch (color){
            case "red": Color.RED.show();break;
            case "blue": Color.BLUE.show();break;
            case "green": Color.GREEN.show();break;
            case "yellow": Color.YELLOW.show();break;
            case "black": Color.BLACK.show();break;
            default: System.out.println("Invalid color"); break;
        }
    }
}
enum Color implements IC {
    RED(255,0,0),
    BLUE(0,0,255),
    GREEN(0,255,0),
    YELLOW(255,255,0),
    BLACK(0,0,0);
    private int redvalue;
    private int greenvalue;
    private int bluevalue;
    Color(int redvalue,int greenvalue,int bluevalue){
        this.redvalue = redvalue;
        this.greenvalue = greenvalue;
        this.bluevalue = bluevalue;
    }
    @Override
    public void show() {
        System.out.println("Color:" + this.redvalue + "," + this.greenvalue + "," + this.bluevalue);
    }
}
interface IC {
    void show();
}