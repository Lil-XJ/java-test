package com.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/24 09:27
 */
public class BallMove extends JFrame {
    MyPanel01 mp = null;
    public static void main(String[] args) {
        new BallMove();
    }
    public BallMove() {
        mp = new MyPanel01();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
class MyPanel01 extends JPanel implements KeyListener {
    int x = 0;
    int y = 0;
 @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval(x, y, 10, 10);
 }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN || keyEvent.getKeyCode() == KeyEvent.VK_S){
            y++;
            this.repaint();
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_UP || keyEvent.getKeyCode() == KeyEvent.VK_W){
            y--;
            this.repaint();
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT || keyEvent.getKeyCode() == KeyEvent.VK_A){
            x--;
            this.repaint();
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT || keyEvent.getKeyCode() == KeyEvent.VK_D){
            x++;
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
