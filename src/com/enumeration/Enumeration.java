package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 */
public class Enumeration {
    public static void main(String[] args) {
        for (Week week:Week.values()){
            System.out.println(week.getName());
        }
    }
}
enum Week{
    MONDAY("星期一"), TUESDAY("星期二"), WEDNESDAY("星期三"), THURSDAY("星期四"), FRIDAY("星期五"), SATURDAY("星期六"), SUNDAY("星期日");
    private String name;
    Week(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
