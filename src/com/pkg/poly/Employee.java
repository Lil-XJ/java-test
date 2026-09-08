package com.pkg.poly;

public class Employee {
    private String name;
    private double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    public Employee(String name, double salary) {
        this.setName(name);
        this.setSalary(salary);
    }
    public String getAnnual(){
        return "12 * " + salary + "=" + (salary * 12);
    }
}
