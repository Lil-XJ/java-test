package tankGame;

import java.util.Vector;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 17:49
 */
public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    public EnemyTank(int x, int y, int direct) {
        super(x, y,direct);
    }
    @Override
    public void run() {
        while(true){
            if(isLive && shots.size() == 0){
                Shot shot = null;
                switch (getDirect()){
                    case 0:
                        shot = new Shot(getX() + 18,getY(),0);
                        break;
                        case 1:
                        shot = new Shot(getX() + 60,getY() + 18,1);
                        break;
                        case 2:
                        shot = new Shot(getX() + 18,getY() + 60,2);
                        break;
                        case 3:
                        shot = new Shot(getX(),getY() + 18,3);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();
            }
            // 每段走随机步数：固定 30 步会把开火点锁死在 150px 网格上，正好绕开玩家
            int steps = 10 + (int)(Math.random() * 31);
            switch (getDirect()){
                case 0:
                    for (int i = 0; i < steps; i++) {
                        moveUp();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < steps; i++) {
                        moveRight();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < steps; i++) {
                        moveDown();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < steps; i++) {
                        moveLeft();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            setDirect((int)(Math.random() * 4));
            if(!isLive){
                break;
            }
        }
    }
}
