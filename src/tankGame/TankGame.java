package tankGame;

import javax.swing.*;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 17:55
 */
public class TankGame extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        new TankGame();

    }
    public TankGame() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(500, 500);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
