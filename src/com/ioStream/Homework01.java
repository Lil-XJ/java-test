package com.ioStream;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/30 11:28
 */
public class Homework01 {
    public static void main(String[] args) {

    }
    @Test
    public void create01() throws Exception {
        String filePath = "E:\\mytemp";
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        String fileName = "hello.txt";
        File file = new File(dir, fileName);
        if(!file.exists()){
            file.createNewFile();
        }else{
            System.out.println("文件已存在");
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("1Hello, World!");
        bw.newLine();
        bw.write("2Hello, World!");
        bw.newLine();
        bw.write("3Hello, World!");
        bw.close();
    }
    @Test
    public void create02() throws Exception {
        String filePath = "E:\\mytemp\\hello.txt";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String len;
        int index = 0;
        while((len = br.readLine()) != null){
            index++;
            System.out.println(index + " " + len);
        }
    }
    @Test
    public void create03() throws Exception {
        String filePath = "src\\dog.properties";
        Properties prop = new Properties();
        prop.load(new FileInputStream(filePath));
        prop.list(System.out);
        Dog dog = new Dog(prop.getProperty("name"), Integer.parseInt(prop.getProperty("age")), prop.getProperty("color"));
        System.out.println(dog);
    }
    public class Dog{
        String name;
        int age;
        String color;
        public Dog(String name, int age, String color) {
            this.name = name;
            this.age = age;
            this.color = color;
        }
        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", color='" + color + '\'' +
                    '}';
        }
    }
}

