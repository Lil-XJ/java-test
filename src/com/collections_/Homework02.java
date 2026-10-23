package com.collections_;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/21 09:31
 */
@SuppressWarnings("all")
public class Homework02 {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(new Car("宝马",400000));
        System.out.println(list);
        list.add(new Car("宾利",5000000));
        list.add(new Car("奥迪",300000));
        System.out.println(list);
        System.out.println(list.contains(new Car("奥迪",300000)));
    }
}
class Car{
    private String name;
    private double price;
    public Car(String name, double price){
        this.name = name;
        this.price = price;
    }
    @Override
    public String toString(){
        return name + "-" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(price, car.price) == 0 && Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
