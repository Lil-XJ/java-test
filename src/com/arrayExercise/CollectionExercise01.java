package com.arrayExercise;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/15 17:29
 */
public class CollectionExercise01 {
    public static void main(String[] args) {
        ArrayList<Dog> list = new ArrayList<>();
        list.add(new Dog("旺财", 1));
        list.add(new Dog("小咪", 2));
        list.add(new Dog("小黑", 4));
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next.toString());
        }
        for (Object obj : list){
            System.out.println(obj.toString());
        }
    }
}
class Dog{
    String name;
    int age;
    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString(){
        return "Dog{name='" + name + "', age=" + age + "}";
    }
    public String haha(){
        return "Dog{name='" + name + "', age=" + age + "}";
    }
}