package com.collections_;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/21 10:13
 */
@SuppressWarnings("all")
public class Homework03 {
    public static void main(String[] args) {
        Map m = new HashMap();
        m.put("jack",650);
        m.put("tom",1200);
        m.put("smith",2900);
        m.put("jack",2600);
        for(Object key:m.keySet()){
            m.put(key,(int)m.get(key) + 100);
            System.out.println(key);
            System.out.println(m.get(key));
        }
    }
}
