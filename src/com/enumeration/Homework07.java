package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 15:55
 */
public class Homework07 {
    public static void main(String[] args) {
        Car car01 = new Car(50);
        new Car(-10);
        new Car(20);
    }
}
class Car {
    private double temperature;
    private Air air;
    public Car(double temperature) {
        this.temperature = temperature;
        this.air = new Air();
        air.flow();
    }
    class Air{
        public void flow(){
            if(temperature > 40){
                System.out.println("Air is flowing 冷气");
            }else if(temperature < 0){
                System.out.println("Air is flowing 热气");
            }else{
                System.out.println("Air is canceling");
            }
        }
    }
}
