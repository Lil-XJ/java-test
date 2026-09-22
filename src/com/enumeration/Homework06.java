package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 14:40
 */
public class Homework06 {
    public static void main(String[] args) {
        Person person = new Person("John", TransportationFactory.getHorse());
        person.getVehicles().work();
        Person person2 = new Person("John", TransportationFactory.getBoat());
        person2.getVehicles().work();
    }
}
interface Vehicles{
    void work();
}
class Horse implements Vehicles{
    @Override
    public void work() {
        System.out.println("Horse is working");
    }
}
class Boat implements Vehicles{
    @Override
    public void work() {
        System.out.println("Boat is working");
    }
}
class TransportationFactory {
    public static Horse getHorse() {
        return new Horse();
    }
    public static Boat getBoat() {
        return new Boat();
    }
}
class Person {
    private Vehicles vehicles;
    private String name;

    public Vehicles getVehicles() {
        return vehicles;
    }

    public String getName() {
        return name;
    }

    Person(String name, Vehicles vehicles) {
        this.name = name;
        this.vehicles = vehicles;
    }
}
