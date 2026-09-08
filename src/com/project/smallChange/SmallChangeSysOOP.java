package com.project.smallChange;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: lxj
 * @date: 2026/9/4
 * @description: 小零钱系统OOP版
 */
public class SmallChangeSysOOP {
    boolean loop = true;
    Scanner scanner = new Scanner(System.in);
    String key = "";
    String details = "\t\t-------零钱通明细------";
    double money = 0;
    double balance = 0;
    Date date = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void mainMenu(){
        do {
            System.out.println("\n\t\t======零钱通菜单======");
            System.out.println("\t\t\t 1 零钱通明细");
            System.out.println("\t\t\t 2 收益入账");
            System.out.println("\t\t\t 3 消费");
            System.out.println("\t\t\t 4 退出");

            System.out.println("请输入你的选择：");
            key = scanner.next();
            switch(key){
                case "1":
                    showDetails();break;
                case "2":
                    addMoney();break;
                case "3":
                    consume();break;
                case "4":
                    String choice;
                    while (true){
                        System.out.println("你确定要退出吗？y/n");
                        choice = scanner.next();
                        if(choice.equals("y") || choice.equals("n")){
                            break;
                        }
                    }
                    if(choice.equals("y")){
                        loop = false;
                    }else{
                        continue;
                    }
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
            }
        } while (loop);
    }
    public void showDetails(){
        System.out.println(details);
    }
    public void addMoney(){
        System.out.println("收益入账金额：");
        money = scanner.nextDouble();
        balance += money;
        date = new Date();
        details += "\n" + "收益入账\t+" + money + "\t" + balance + "\t" + format.format(date);

    }
    public void consume(){
        System.out.println("消费金额：");
        money = scanner.nextDouble();
        System.out.println("消费说明：");
        String remark = scanner.next();
        balance -= money;
        date = new Date();
        details += "\n" + remark + "\t-" + money + "\t" + balance + "\t" + format.format(date);

    }
}
