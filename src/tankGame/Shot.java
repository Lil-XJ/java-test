package tankGame;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/27 14:14
 */
public class Shot implements Runnable{
    int x;
    int y;
    int direct = 0;
    int speed = 5;
    boolean isLive = true;
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direct){
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            if(x < 0 || y < 0 || x > 500 || y > 500){
                isLive = false;
                break;
            }
        }
    }
}
