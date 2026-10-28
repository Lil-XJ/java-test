package tankGame;

import java.util.Vector;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 17:49
 */
public class EnemyTank extends Tank{
    Vector<Shot> shots = new Vector<>();
    public EnemyTank(int x, int y, int direct) {
        super(x, y,direct);
    }
}
