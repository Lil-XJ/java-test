package tankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

/**
 * @author 连晓杰
 * @version 1.0
 * @since 2026/10/23 17:46
 */
public class MyPanel extends JPanel implements KeyListener,Runnable {
    static MyTank myTank = null;
    static Vector<EnemyTank> enemyTanks = new Vector<>();
    static Vector<Bomb> bombs = new Vector<>();
    Image image01 = null;
    Image image02 = null;
    Image image03 = null;
    public  MyPanel (){
        myTank = new MyTank(250, 400, 0);
        for (int i = 0; i < 3; i++){
            enemyTanks.add(new EnemyTank(100 + i * 100, 10, 2));
            Shot shot = new Shot(enemyTanks.get(i).getX() + 18, enemyTanks.get(i).getY() + 60, enemyTanks.get(i).getDirect());
            enemyTanks.get(i).shots.add(shot);
            new Thread(enemyTanks.get(i)).start();
            new Thread(shot).start();
        }

    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(new Color(0x2B, 0x2F, 0x2B));
        g.fillRect(0,0,500,500);
        if(myTank.isLive){
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 1);
        }
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if(shot != null && shot.isLive){
                g.fill3DRect(shot.x, shot.y, 5, 5, false);
            }else{
                myTank.shots.remove(shot);
            }
        }


        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if(bomb.life > 6){
                g.drawImage(image01, bomb.x, bomb.y, 60,60,this);
            } else if(bomb.life > 3){
                g.drawImage(image02, bomb.x, bomb.y, 60,60,this);
            } else {
                g.drawImage(image03, bomb.x, bomb.y, 60,60,this);
            }
            bomb.lifeDown();
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }
        
        for (EnemyTank o:enemyTanks){
            if(o.isLive){
                drawTank(o.getX(), o.getY(), g, o.getDirect(), 0);
            }
            for (int j = 0; j < o.shots.size(); j++) {
                Shot shot = o.shots.get(j);
                if(shot != null && shot.isLive){
                    g.fill3DRect(shot.x, shot.y, 5, 5, true);
                }else{
                    o.shots.remove(shot);
                }
            }

        }
        image01 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/explosion_frame1_transparent.png"));
        image02 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/explosion_frame2_transparent.png"));
        image03 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/explosion_frame3_transparent.png"));

    }





    /**
     * 绘制坦克（俯视平面 2D）
     * <p>
     * 分层叠出体积感，由下到上依次：影子 → 履带 → 车体 → 炮管 → 炮塔，
     * 每层比下一层亮一档，光源统一在左上角。炮管先画、炮塔后画，
     * 炮管根部自然被炮塔盖住，看起来就是「从炮塔里伸出来」。
     * <p>
     * 四个朝向共用同一套「炮口朝上」的画法，靠绕包围盒中心旋转得到，
     * 所以各方向细节完全一致。局部坐标原点 = 坦克中心，车头朝 -y。
     * (x, y) 是包围盒左上角：上/下方向占 40x60，左/右方向占 60x40。
     *
     * @param x      包围盒左上角 x
     * @param y      包围盒左上角 y
     * @param g      画笔
     * @param direct 朝向：0-上 1-右 2-下 3-左
     * @param type   阵营：0-我方(青色) 1-敌方(红色)
     */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        // 阵营基色，下面所有明暗层级都从它派生
        Color base;
        switch (type){
            case 0:
                base = Color.cyan;
                break;
            case 1:
                base = Color.red;
                break;
            default:
                base = Color.gray;
                break;
        }

        // 朝向 → 旋转角 + 包围盒中心
        double theta, cx, cy;
        switch (direct){
            case 0: theta = 0;               cx = x + 20; cy = y + 30; break; // 上
            case 1: theta = Math.PI / 2;     cx = x + 30; cy = y + 20; break; // 右
            case 2: theta = Math.PI;         cx = x + 20; cy = y + 30; break; // 下
            case 3: theta = Math.PI * 3 / 2; cx = x + 30; cy = y + 20; break; // 左
            default:
                System.out.println("暂未处理");
                return;
        }

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        Object oldAA = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(cx, cy);
        g2.rotate(theta);

        drawTankUp(g2, base);

        g2.setTransform(old);
        if (oldAA != null) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
        }
    }

    /**
     * 按「炮口朝上」画一整台坦克。调用方负责平移/旋转，局部原点是坦克中心。
     *
     * @param g2   已定位好的画笔
     * @param base 阵营基色
     */
    private void drawTankUp(Graphics2D g2, Color base){
        // 中间调，再往黑/白拉出层级，保证「越靠上越亮」有一整条亮度梯度
        Color mid       = mix(base, Color.BLACK, 0.20f);
        Color treadEdge = mix(mid, Color.BLACK, 0.62f); // 最暗：履带描边
        Color tread     = mix(mid, Color.BLACK, 0.45f);
        Color treadMark = mix(mid, Color.BLACK, 0.28f);
        Color hullEdge  = mix(mid, Color.BLACK, 0.38f);
        Color hullMain  = mix(mid, Color.BLACK, 0.15f);
        Color hullTop   = mid;
        Color barrelTip = mix(mid, Color.BLACK, 0.50f);
        Color barrel    = mix(mid, Color.BLACK, 0.25f);
        Color barrelHi  = mix(mid, Color.WHITE, 0.40f);
        Color turEdge   = mix(mid, Color.BLACK, 0.32f);
        Color turMain   = mix(mid, Color.WHITE, 0.25f);
        Color turInner  = mix(mid, Color.WHITE, 0.55f);
        Color turSpec   = mix(mid, Color.WHITE, 0.80f);

        // 0. 影子：往右下偏一点，跟左上角光源呼应
        g2.setColor(new Color(0, 0, 0, 70));
        g2.fill(new RoundRectangle2D.Double(-16, -23, 36, 52, 14, 14));

        // 1. 履带：最底层、最暗。先描边再主体，边上留一圈深色缝
        g2.setColor(treadEdge);
        g2.fill(new RoundRectangle2D.Double(-20, -30, 11, 60, 6, 6));
        g2.fill(new RoundRectangle2D.Double(9, -30, 11, 60, 6, 6));
        g2.setColor(tread);
        g2.fill(new RoundRectangle2D.Double(-19, -29, 9, 58, 5, 5));
        g2.fill(new RoundRectangle2D.Double(10, -29, 9, 58, 5, 5));
        // 履带板横纹，给最底层加点质感
        g2.setColor(treadMark);
        for (int i = 0; i < 7; i++) {
            int ty = -26 + i * 8;
            g2.fill(new RoundRectangle2D.Double(-18, ty, 7, 4, 2, 2));
            g2.fill(new RoundRectangle2D.Double(11, ty, 7, 4, 2, 2));
        }

        // 2. 车体：中间层。深色底 + 主体 + 亮色顶板，自己也分三层
        g2.setColor(hullEdge);
        g2.fill(new RoundRectangle2D.Double(-12, -22, 24, 44, 8, 8));
        g2.setColor(hullMain);
        g2.fill(new RoundRectangle2D.Double(-10, -20, 20, 40, 6, 6));
        g2.setColor(hullTop);
        g2.fill(new RoundRectangle2D.Double(-7, -14, 14, 26, 5, 5));

        // 3. 炮管：先画，根部待会儿被炮塔盖住，形成「从炮塔伸出」
        g2.setColor(barrel);
        g2.fill(new RoundRectangle2D.Double(-3, -30, 6, 24, 3, 3));
        g2.setColor(barrelTip);
        g2.fill(new RoundRectangle2D.Double(-4, -30, 8, 7, 2, 2));
        g2.setColor(barrelHi);
        g2.fill(new RoundRectangle2D.Double(-2.3, -28, 1.8, 20, 1, 1));

        // 4. 炮塔：最上层、最亮。同心圆做出穹顶，高光点在左上
        g2.setColor(turEdge);
        g2.fill(new Ellipse2D.Double(-12, -12, 24, 24));
        g2.setColor(turMain);
        g2.fill(new Ellipse2D.Double(-11, -11, 22, 22));
        g2.setColor(turInner);
        g2.fill(new Ellipse2D.Double(-7, -7, 14, 14));
        g2.setColor(turSpec);
        g2.fill(new Ellipse2D.Double(-5, -6, 7, 7));
    }

    /**
     * 把 a 按比例 t 向 b 混合，t=0 得 a，t=1 得 b。用来从阵营基色派生明暗。
     */
    private static Color mix(Color a, Color b, float t){
        int r = (int) (a.getRed()   + (b.getRed()   - a.getRed())   * t);
        int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue()  - a.getBlue())  * t);
        return new Color(r, g, bl);
    }
    /**
     * 判断子弹是否命中坦克（矩形重叠），敌我通用：命中即击杀并生成爆炸。
     * <p>
     * 坦克包围盒随朝向变化：上/下 40x60，左/右 60x40；子弹是 5x5 的小方块。
     * 两个轴都相交才算命中 —— 只查垂直于行进方向的一个轴，
     * 会让同一行/列上隔多远的子弹都隔空命中。朝向只决定包围盒宽高。
     * <p>
     * 只负责判中和击杀，不动任何坦克列表：我方坦克不在 enemyTanks 里，
     * 把敌方移出列表是 {@link #hitEnemyTank()} 的事。
     *
     * @param s      子弹
     * @param target 被打的坦克（敌我均可）
     */
    public static void hitTank(Shot s, Tank target){
        boolean upDown = target.getDirect() == 0 || target.getDirect() == 2;
        int w = upDown ? 40 : 60;
        int h = upDown ? 60 : 40;
        if (s.x + 5 > target.getX() && s.x < target.getX() + w
                && s.y + 5 > target.getY() && s.y < target.getY() + h) {
            s.isLive = false;
            target.isLive = false;
            Bomb bomb = new Bomb(target.getX(), target.getY());
            bombs.add(bomb);
        }
    }
    public static void hitMyTank(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
               if(myTank.isLive && shot.isLive){
                   hitTank(shot, myTank);
               }
            }
        }
    }
    /**
     * 我方所有在飞的子弹 vs 敌方坦克；命中后把敌方从 enemyTanks 移除。
     * 不能只查 {@link MyTank#shot}：那只是最后一发，先前打出去的子弹同样要算数。
     */
    public static void hitEnemyTank(){
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if(shot == null || !shot.isLive){
                continue;
            }
            for (int j = 0; j < enemyTanks.size(); j++) {
                EnemyTank enemyTank = enemyTanks.get(j);
                if(!enemyTank.isLive || !shot.isLive){
                    continue;
                }
                hitTank(shot, enemyTank);
                if(!enemyTank.isLive){
                    enemyTanks.remove(enemyTank);
                }
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(!myTank.isLive){
            return; // 坦克已被击毁（爆炸后消失），不再响应操作
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_W){
            myTank.moveUp();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            myTank.moveDown();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
            myTank.moveLeft();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_D) {
            myTank.moveRight();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_J) {
//            if(myTank.shot == null || !myTank.shot.isLive){
                myTank.shot();
//            }
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hitEnemyTank();
            hitMyTank();
            this.repaint();
        }
    }
}
