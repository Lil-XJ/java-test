package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 11:33
 */
public class Homework03 {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.shout();
        Dog dog = new Dog();
        dog.shout();
    }
}
abstract class Animal {
    public abstract void shout();
}
class Cat extends Animal{
    @Override
    public void shout(){
        System.out.println("Meow");
    }
}
class Dog extends Animal{
    @Override
    public void shout(){
        System.out.println("Woof");
    }
}

