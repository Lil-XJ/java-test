import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {

    private static final int TILE_SIZE = 25;
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 600;
    private static final int TOTAL_TILES = (BOARD_WIDTH * BOARD_HEIGHT) / (TILE_SIZE * TILE_SIZE);

    private final ArrayList<Point> snake = new ArrayList<>();
    private Point food;
    private char direction = 'R'; // U, D, L, R
    private boolean running = false;
    private boolean gameOver = false;
    private int score = 0;
    private int highScore = 0;
    private Timer timer;
    private final Random random = new Random();

    // Speed: lower = faster (milliseconds per tick)
    private static final int INITIAL_DELAY = 120;
    private static final int MIN_DELAY = 60;

    public SnakeGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(30, 30, 30));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        startGame();
    }

    private void startGame() {
        snake.clear();
        // Start in the middle, length 3
        int startX = (BOARD_WIDTH / TILE_SIZE) / 2;
        int startY = (BOARD_HEIGHT / TILE_SIZE) / 2;
        snake.add(new Point(startX, startY));
        snake.add(new Point(startX - 1, startY));
        snake.add(new Point(startX - 2, startY));

        direction = 'R';
        score = 0;
        gameOver = false;
        running = true;

        spawnFood();

        if (timer != null) timer.stop();
        timer = new Timer(INITIAL_DELAY, this);
        timer.start();
    }

    private void spawnFood() {
        int maxCols = BOARD_WIDTH / TILE_SIZE;
        int maxRows = BOARD_HEIGHT / TILE_SIZE;
        Point newFood;
        do {
            newFood = new Point(random.nextInt(maxCols), random.nextInt(maxRows));
        } while (snake.contains(newFood));
        food = newFood;
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead = switch (direction) {
            case 'U' -> new Point(head.x, head.y - 1);
            case 'D' -> new Point(head.x, head.y + 1);
            case 'L' -> new Point(head.x - 1, head.y);
            case 'R' -> new Point(head.x + 1, head.y);
            default -> head;
        };

        snake.add(0, newHead);

        if (newHead.equals(food)) {
            score += 10;
            if (score > highScore) highScore = score;
            spawnFood();
            // Speed up slightly
            int newDelay = Math.max(MIN_DELAY, INITIAL_DELAY - (score / 50) * 10);
            timer.setDelay(newDelay);
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private boolean checkCollision() {
        Point head = snake.get(0);
        int maxCols = BOARD_WIDTH / TILE_SIZE;
        int maxRows = BOARD_HEIGHT / TILE_SIZE;

        // Wall collision
        if (head.x < 0 || head.x >= maxCols || head.y < 0 || head.y >= maxRows) {
            return true;
        }

        // Self collision
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                return true;
            }
        }
        return false;
    }

    private void handleKeyPress(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                startGame();
            }
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                if (direction != 'D') direction = 'U';
            }
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                if (direction != 'U') direction = 'D';
            }
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                if (direction != 'R') direction = 'L';
            }
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                if (direction != 'L') direction = 'R';
            }
            case KeyEvent.VK_SPACE -> {
                running = !running;
                if (running) timer.start();
                else timer.stop();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !gameOver) {
            move();
            if (checkCollision()) {
                gameOver = true;
                running = false;
                timer.stop();
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!gameOver) {
            drawGame(g2d);
        } else {
            drawGameOver(g2d);
        }
    }

    private void drawGame(Graphics2D g) {
        int maxCols = BOARD_WIDTH / TILE_SIZE;
        int maxRows = BOARD_HEIGHT / TILE_SIZE;

        // Draw grid (subtle)
        g.setColor(new Color(40, 40, 40));
        for (int x = 0; x <= maxCols; x++) {
            g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, BOARD_HEIGHT);
        }
        for (int y = 0; y <= maxRows; y++) {
            g.drawLine(0, y * TILE_SIZE, BOARD_WIDTH, y * TILE_SIZE);
        }

        // Draw food with glow effect
        g.setColor(new Color(255, 50, 50, 60));
        g.fillOval(food.x * TILE_SIZE - 4, food.y * TILE_SIZE - 4, TILE_SIZE + 8, TILE_SIZE + 8);
        g.setColor(new Color(255, 60, 60));
        g.fillRoundRect(food.x * TILE_SIZE + 2, food.y * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4, 8, 8);

        // Draw snake
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            if (i == 0) {
                // Head - brighter
                g.setColor(new Color(80, 220, 80));
                g.fillRoundRect(p.x * TILE_SIZE + 1, p.y * TILE_SIZE + 1, TILE_SIZE - 2, TILE_SIZE - 2, 10, 10);
                // Eyes
                g.setColor(Color.WHITE);
                int eyeSize = 5;
                if (direction == 'R') {
                    g.fillOval(p.x * TILE_SIZE + 15, p.y * TILE_SIZE + 5, eyeSize, eyeSize);
                    g.fillOval(p.x * TILE_SIZE + 15, p.y * TILE_SIZE + 15, eyeSize, eyeSize);
                } else if (direction == 'L') {
                    g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 5, eyeSize, eyeSize);
                    g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 15, eyeSize, eyeSize);
                } else if (direction == 'U') {
                    g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 5, eyeSize, eyeSize);
                    g.fillOval(p.x * TILE_SIZE + 15, p.y * TILE_SIZE + 5, eyeSize, eyeSize);
                } else {
                    g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 15, eyeSize, eyeSize);
                    g.fillOval(p.x * TILE_SIZE + 15, p.y * TILE_SIZE + 15, eyeSize, eyeSize);
                }
            } else {
                // Body - gradient from bright to dark green
                int green = 180 - (i * 2);
                green = Math.max(green, 80);
                g.setColor(new Color(40, green, 40));
                g.fillRoundRect(p.x * TILE_SIZE + 2, p.y * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4, 8, 8);
            }
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("得分: " + score, 10, 22);
        g.setColor(new Color(180, 180, 180));
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString("最高分: " + highScore, 10, 40);

        // Pause indicator
        if (!running) {
            g.setColor(new Color(0, 0, 0, 120));
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));
            String pauseText = "已暂停";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(pauseText, (BOARD_WIDTH - fm.stringWidth(pauseText)) / 2, BOARD_HEIGHT / 2);
            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            String hint = "按 空格键 继续";
            fm = g.getFontMetrics();
            g.drawString(hint, (BOARD_WIDTH - fm.stringWidth(hint)) / 2, BOARD_HEIGHT / 2 + 30);
        }
    }

    private void drawGameOver(Graphics2D g) {
        // Semi-transparent overlay
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        // Game Over box
        int boxW = 320, boxH = 200;
        int boxX = (BOARD_WIDTH - boxW) / 2;
        int boxY = (BOARD_HEIGHT - boxH) / 2;
        g.setColor(new Color(40, 40, 40, 220));
        g.fillRoundRect(boxX, boxY, boxW, boxH, 20, 20);
        g.setColor(new Color(100, 100, 100));
        g.drawRoundRect(boxX, boxY, boxW, boxH, 20, 20);

        // Title
        g.setColor(new Color(255, 70, 70));
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        String title = "游戏结束";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, (BOARD_WIDTH - fm.stringWidth(title)) / 2, boxY + 55);

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));
        String scoreText = "得分: " + score;
        fm = g.getFontMetrics();
        g.drawString(scoreText, (BOARD_WIDTH - fm.stringWidth(scoreText)) / 2, boxY + 95);

        // High score
        g.setColor(new Color(255, 215, 0));
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        String highText = "最高分: " + highScore;
        fm = g.getFontMetrics();
        g.drawString(highText, (BOARD_WIDTH - fm.stringWidth(highText)) / 2, boxY + 125);

        // Restart hint
        g.setColor(new Color(180, 180, 180));
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String hint = "按 空格键 或 回车 重新开始";
        fm = g.getFontMetrics();
        g.drawString(hint, (BOARD_WIDTH - fm.stringWidth(hint)) / 2, boxY + 170);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("贪吃蛇");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new SnakeGame());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
