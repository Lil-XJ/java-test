package com.pkg.poly;

import com.pkg.poly.Employee;
public class Worker extends Employee{
    public Worker(String name, double salary) {
        super(name, salary);
    }
    public void work(){
        System.out.println(getName() + "正在工作");
    }
}
