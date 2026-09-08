package com.pkg.override;

public class Override {
    public static void main(String[] args) {
        Student student = new Student("001", 99.5, "Alice", 20);
        System.out.println(student.say());
    }
}
class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String say(){
        return "Person [name=" + name + ", age=" + age + "]";
    }
}
class Student extends Person {
    private String id;
    private double score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public Student(String id, double score,String name,int age){
        super(name, age);
        this.id = id;
        this.score = score;
    }
    public String say(){
        return "Student [id=" + id + ", score=" + score + ", name=" + getName() + ", age=" + getAge() + "]";
    }
}

