package com.pkg.extend_;

public class Extend {
    public static void main(String[] args) {
        PC pc = new PC("Intel i7", "16GB", 1024, "Dell");
        pc.display();
        NotePad np = new NotePad("Intel i5", "8GB", 512, "White");
        np.display();
    }
}

class Computer{
    String CPU;
    String RAM;
    int Storage;
    public Computer(String CPU, String RAM, int Storage) {
        this.CPU = CPU;
        this.RAM = RAM;
        this.Storage = Storage;
    }
    public void display(){
        System.out.println("CPU: "+CPU);
        System.out.println("RAM: "+RAM);
        System.out.println("Storage: "+Storage);
    }
}
class PC extends Computer{
    String brand;
    public PC(String CPU, String RAM, int Storage, String brand){
        super(CPU, RAM, Storage);
        this.brand = brand;
    }
    public void display(){
        System.out.println("CPU: "+CPU);
        System.out.println("RAM: "+RAM);
        System.out.println("Storage: "+Storage);
        System.out.println("Brand: "+brand);
    }
}
class NotePad extends Computer{
    String color;
    public NotePad(String CPU, String RAM, int Storage, String color){
        super(CPU, RAM, Storage);
        this.color = color;
    }
    public void display(){
        System.out.println("Color: "+color);
    }
}