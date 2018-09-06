import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Field extends JPanel implements ActionListener {

    private final int SIZE = 320;
    private final int DOT_SIZE = 16;  //размер пикселей змейки и яблока
    private final int ALL_DOTS = 400;

    private Image dot;
    private Image apple;

    //позиция яблока
    private int appleX;
    private int appleY;

    //положение змеи
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    //размер змеи
    private int dots;

    //таймер
    private Timer timer;

    //текущее направление движения змеи
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public Field() {
        setBackground(Color.BLACK); //цвет игрового поля
        loadImages();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    //загрузка изображений
    public void loadImages() {
        ImageIcon imageApple = new ImageIcon("apple.png");
        apple = imageApple.getImage();

        ImageIcon imageDot = new ImageIcon("dot.png");
        dot = imageDot.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }else {
            String str = "Game Over";
            //Font f = new Font("Arial", 14, Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str, 125, SIZE/2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        } else if (right) {
            x[0] += DOT_SIZE;
        } else if (up) {
            y[0] -= DOT_SIZE;
        } else if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE) {
            inGame = false;
        } else if (x[0] < 0) {
            inGame = false;
        } else if (y[0] > SIZE) {
            inGame = false;
        } else if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    //обработка нажатия клавиш
    class FiledKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            else if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            else if (key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            else if (key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}