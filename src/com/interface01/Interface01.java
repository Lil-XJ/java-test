package com.interface01;

public class Interface01 {
    public static void main(String[] args) {
        A a = new A();
        a.hello();
    }
}
class A implements MyInterface{
    public void hello() {
        System.out.println("hello");
    }
}

