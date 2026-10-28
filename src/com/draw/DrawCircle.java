package com.draw;

import javax.swing.*;
import java.awt.*;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 15:59
 */
public class DrawCircle extends JFrame{
    private MyPanel mp = null;
    public static void main(String[] args) {
        new DrawCircle();
    }
    public DrawCircle(){
        mp = new MyPanel();
        this.add(mp);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
class MyPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        g.drawOval(10, 10, 100, 100);
        Image img = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/icon.png"));
        g.drawImage(img, 10, 10, 1184 / 3, 776 / 3, this);
    }
}
