package com.enumeration;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/9/20 11:39
 */
public class Homework04 {
    public static void main(String[] args) {
        Cellphone cell = new Cellphone();
        cell.testWork(new Calculator() {
            @Override
            public void work(double n1, double n2) {
                System.out.println("Calculator is working" + (n1 + n2));
            }
        },10,20);
    }
}
interface Calculator {
    void work(double n1,double n2);
}
class Cellphone{
   public void testWork(Calculator calc,double n1,double n2){
       System.out.println(calc.getClass().getName());
       calc.work(n1,n2);
   }
}
