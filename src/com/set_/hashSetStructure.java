package com.set_;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/17 15:03
 */
public class hashSetStructure {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        set.add(new Employee("tom", 18));
        set.add(new Employee("jack", 18));
        set.add(new Employee("tom", 18));
        System.out.println(set);
    }
}
class Employee {
    private String name;
    private int age;
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
