package com.generic_;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/21 14:15
 */
@SuppressWarnings("all")
public class Generic {
    public static void main(String[] args) {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("b", 5000, new MyDate(1990, 1, 1)));
        list.add(new Employee("a", 2000, new MyDate(2001, 2, 3)));
        list.add(new Employee("b", 3000, new MyDate(2003, 4, 5)));
        for(Employee e:list){
            System.out.println(e.toString());
        }
        list.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                int i = e1.getName().compareTo(e2.getName());
                if(i != 0) return i;
                return e1.getBirthday().compareTo(e2.getBirthday());
            }
        });
        System.out.println(list.toString());
    }
}
class Employee{
    private String name;
    private double sal;
    private MyDate birthday;

    public Employee(String name, double sal, MyDate birthday) {
        this.name = name;
        this.sal = sal;
        this.birthday = birthday;
    }
   @Override
   public String toString(){
        return name + "\t" + sal + "\t" + birthday.toString();
   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }
}
class MyDate implements Comparable<MyDate>{
    private int year;
    private int month;
    private int day;
    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    @Override
    public String toString(){
        return year + "-" + month + "-" + day;
    }
    @Override
    public int compareTo(MyDate other) {
        return this.year - other.year;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

