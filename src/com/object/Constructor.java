package com.object;

import java.util.Arrays;

public class Constructor {
    public static void main(String[] args) {
      /*  double[] d1 = {4.5,1.1,1.2,2.2};
        A01 a1 = new A01();
        double maxNum = a1.max(d1);
        System.out.println("最大值为：" + maxNum);*/

       /* String[] s1 = {"123","456","abc"};
        A02 a2 = new A02();
        System.out.println(a2.find(s1,"1"));*/

        Circle circle = new Circle(2);
        double area = circle.getArea();
        double perimeter = circle.getPerimeter();
        System.out.println(area + "--" + perimeter);
    }
}
class A01 {

    public double max(double[] arr){
        for (int i = 0;i < arr.length;i++){
            for(int j = 0;j < i;j++){
                double temp;
                if(arr[j] < arr[j + 1]){
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));

        return arr[0];
    }
}
class A02 {
    public int find(String[] arr,String str){
        int index = -1;
        for (int i = 0;i < arr.length;i++){
            if(arr[i].equals(str)){
                index = i;
            }
        }
        return index;
    }
}
class Circle{
    double radius;
    public Circle(double radius){
        this.radius = radius;
    }
    public double getPerimeter(){
        return Math.PI * 2 * radius;
    }
    public double getArea(){
        return Math.PI * Math.pow(radius,2) ;
    }

}
class Employee{
    String name;
    char sex;
    int age;
    String job;
    double salary;
   public Employee(String name,char sex,int age,String job,double salary){
        this(name,sex,age);
       this.job = job;
       this.salary = salary;
    }
    public Employee(String name,char sex,int age){
        this.age = age;
        this.name = name;
        this.sex = sex;
    }
    public Employee(String job,double salary){
        this.job = job;
        this.salary = salary;
    }
}