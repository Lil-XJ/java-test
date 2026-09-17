package com.innerClass;

public class InnerClass {
    public static void main(String[] args) {
        Cellphone cellphone = new Cellphone();
        cellphone.alarmClock(new Bell(){
            @Override
            public void ring(){
                System.out.println(" 懒猪起床了 " + this.getClass());
            }
        });
        cellphone.alarmClock(new Bell(){
            @Override
            public void ring(){
                System.out.println(" 小伙伴上课了 " + this.getClass());
            }
        });
    }

}
class Cellphone{
    public void alarmClock(Bell bell){
        bell.ring();
    }
}
interface Bell{
    void ring();
}

