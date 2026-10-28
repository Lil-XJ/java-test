package tankGame;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 17:47
 */
public class Tank {
    private int x;
    private int y;
    private int direct;
    boolean isLive = true; // 标识坦克是否还活着

    public Tank(int x, int y,int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
    public void moveUp(){
        this.setDirect(0);
        if(this.getY() > 0){
            this.setY(this.getY() - 5);
        }
    }
    public void moveDown(){
        this.setDirect(2);
        if(this.getY() < 500){
            this.setY(this.getY() + 5);
        }
    }
    public void moveLeft(){
        this.setDirect(3);
        if(this.getX() > 0){
            this.setX(this.getX() - 5);
        }
    }
    public void moveRight(){
        this.setDirect(1);
        if(this.getX() < 500){
            this.setX(this.getX() + 5);
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
