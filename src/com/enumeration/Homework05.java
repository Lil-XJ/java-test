package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 14:27
 */
public class Homework05 {
    public static void main(String[] args) {
        A a = new A("A name");
        a.test();
    }
}
class A {
    private String name;
    public A(String name) {
        this.name = name;
    }
    public void test(){
        class B{
            private String name;
            public  B(String name){
                this.name = name;
            }
            public void show(){
                System.out.println(this.name + A.this.name);
            }
        }
        B b = new B("B name");
        b.show();
    }
}
