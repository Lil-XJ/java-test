package tankGame;

import java.util.Vector;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 17:49
 */
public class MyTank extends Tank{
    public Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    public MyTank(int x, int y,int direct) {
        super(x, y,direct);
    }
    public void shot(){
        switch (getDirect()){
            case 0:
                shot = new Shot(getX() + 18, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 18, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }
}
