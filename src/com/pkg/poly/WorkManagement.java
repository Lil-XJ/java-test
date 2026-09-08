package com.pkg.poly;

public class WorkManagement {
    public static void main(String[] args) {
        Employee e = new Manager("张三", 5000, 1000);
        Employee e2 = new Worker("李四", 3000);
        WorkManagement w = new WorkManagement();
        System.out.println(w.showEmpAnnal(e));
        System.out.println(w.showEmpAnnal(e2));
        w.testWork(e);
        w.testWork(e2);
        String name1 = "123";
        String name2 = "456";
        name1.equals(name2);
        System.out.println(name1 + " " + Integer.toHexString(e.hashCode()) );
    }
    public String showEmpAnnal(Employee e){
        return e.getAnnual();
    }
    public void testWork(Employee e){
        if(e instanceof Worker){
            ((Worker)e).work();
        }else if(e instanceof Manager){
            ((Manager)e).manage();
        }
    }
}
