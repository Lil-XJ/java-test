package com.generic_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 11:51
 */
public class Homework01 {
    public static void main(String[] args) {

    }
    @Test
    public void testList(){
        DAO<User> dao = new DAO<>();
        dao.save("1",new User("张三",18,1));
        dao.save("2",new User("wangwu",19,2));
        System.out.println(dao.get("1"));
        System.out.println(dao.list());
    }
}
class DAO<T>{
    private Map<String,T> map = new HashMap<>();

    public void save(String id,T entity){
        map.put(id,entity);
    }
    public T get(String id){
        return map.get(id);
    }
    public void update(String id,T entity){
        if (map.containsKey(id)){
            map.put(id,entity);
        }
    }
    public List<T> list(){
        return new ArrayList<T>(map.values());
    }
    public void delete(String id){
        map.remove(id);
    }
}
class User{
    private String name;
    private int age;
    private int id;
    public User(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
