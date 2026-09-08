package object;

public class MonkeyEatPeach {
    public static void main(String[] args) {
        /*
        * 第10天   1个
        * 第9天 4个
        * 第8天 10个
        * 第7天 22个
        * */

        Monkey obj = new Monkey();
        System.out.println(obj.calcPeachNum(4));
    }
}
class Monkey{
    public int calcPeachNum(int day){
        if(day > 1){
            return (calcPeachNum(day -1)+ 1) * 2 ;
        }else{
            return 1;
        }

    }
}
