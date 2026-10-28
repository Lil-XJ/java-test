package tankGame;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/27 17:57
 */
public class Bomb {
    int x,y;
    int life = 9;
    boolean live = true;
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){
        if(life>0)
            life--;
        else
            live = false;
    }
}
