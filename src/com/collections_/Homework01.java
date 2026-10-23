package com.collections_;

import java.util.ArrayList;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/21 08:43
 */
@SuppressWarnings("all")
public class Homework01 {
    public static void main(String[] args) {
        News news01 = new News("新冠确诊病例超千万，数百万印度教信徒赴恒河“圣浴”引民众担忧");
        News news02 = new News("男子突然想起2个月前钓的鱼还在网兜里，捞起一看赶紧放生");
        ArrayList list = new ArrayList();
        list.add(news01);
        list.add(news02);
        for (int i = list.size() - 1; i >= 0; i--) {
            String title = ((News)list.get(i)).getTitle();
            if(title.length() > 15){
               title = title.substring(0, 15) + "...";
            }
            System.out.println(title);
        }
    }
}
class News{
    private String title;
    private String content;
    @Override
    public String toString(){
        return this.title;
    }
    public News(String title){
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
