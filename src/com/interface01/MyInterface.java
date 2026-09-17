package com.interface01;

public interface MyInterface {
    public int num = 10;
    default public void hello(){
        System.out.println("这是默认函数hello");
    };
}
