package com.project.test;

public class Homework01 {
    /*
    * public
    * protected
    * 默认
    * private
    * */
    public static void main(String[] args) {
        Person[] person = new Person[4];
        person[0] = new Person("张三", 20, "学生");
        person[1] = new Person("李四", 18, "教师");
        person[2] = new Person("王五2", 25, "程序员");
        person[3] = new Person("王五", 22, "程序员");
        for (int i = 0; i < person.length - 1; i++) {
            for (int j = 0; j < person.length - 1 - i; j++) {
                if(person[j].age < person[j + 1].age ){
                    System.out.print(person[j].name + " " + person[j].age + " " + person[j].job);
                    System.out.println(person[j + 1].name + " " + person[j + 1].age + " " + person[j + 1].job);
                    Person temp = person[j];
                    person[j] = person[j + 1];
                    person[j + 1] = temp;
                }
            }
        }
        for (Person p : person) {
            System.out.println(p.name + " " + p.age + " " + p.job);
        }
    }
}
