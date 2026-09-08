package com.project.test;

public class Homework03 {
    public static void main(String[] args) {
        Professor professor = new Professor("张三", 50, "教授", 10000);
        AssociateProfessor associateProfessor = new AssociateProfessor("张三", 50, "副教授", 10000);
        professor.introduce();
        associateProfessor.introduce();
    }
}
class Teacher{
    String name;
    int age;
    double salary;
    String post;
    public Teacher(String name, int age, String post, double salary) {
        this.name = name;
        this.age = age;
        this.post = post;
        this.salary = salary;
    }
    public void introduce(){
        System.out.println("姓名：" + name + "，年龄：" + age + "，职位：" + post + "，薪水：" + salary);
    }
}

class Professor extends Teacher{
    public Professor(String name, int age, String post, double salary) {
        super(name, age, post, salary);
    }
    @Override
    public void introduce(){
        System.out.println("姓名：" + name + "，年龄：" + age + "，职位：" + post + "，薪水：" + salary * 1.3);
    }
}
class AssociateProfessor extends Teacher{
    public AssociateProfessor(String name, int age, String post, double salary) {
        super(name, age, post, salary);
    }
    @Override
    public void introduce(){
        System.out.println("姓名：" + name + "，年龄：" + age + "，职位：" + post + "，薪水：" + salary * 1.2);
    }
}
