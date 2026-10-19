package com.arrayExercise;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/13 16:00
 */
public class ArrayExercise01 {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add("hello");
        list.add("world");
        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.contains("hello"));
        list.remove("hello");
        System.out.println(list);

        ArrayList list2 = new ArrayList();
        list2.add("123");
        list2.add("456");
        list.addAll(list2);
        System.out.println(list);
        System.out.println(list.containsAll(list2));
        list.removeAll(list2);
        System.out.println(list);

        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            Object obj = iterator.next();
            System.out.println("obj:" + obj);
        }
        for(Object obj : list){
            System.out.println("obj:" + obj);
        }
    }
}
