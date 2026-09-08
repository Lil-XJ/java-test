package com.pkg.poly;

public class Manager extends Employee{
    private double bonus;
    public double getBonus() {
        return bonus;
    }
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.setBonus(bonus);
    }
    public void manage(){
        System.out.println(super.getName() + "管理员工");
    }
    @Override
    public String getAnnual(){
        return "12 * " + super.getSalary() + " + " + this.bonus + "=" + (super.getSalary() * 12 + bonus);
    }
}
