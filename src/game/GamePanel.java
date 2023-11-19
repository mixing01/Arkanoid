package game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    Image img = Toolkit.getDefaultToolkit().getImage("src\\rsc\\background.png");
    final int FPS = 60;
    private static final int screenWidth = 1280;
    private static final int screenHeight = 720;
    KeyboardHandler kH = new KeyboardHandler();
    Thread gameThread;

    public static int getScreenWidth() {
        return  screenWidth;
    }
    public static int getScreenHeight() {
        return screenHeight;
    }

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        Field.initBlocks();
        this.setBackground(new Color(161, 255, 253, 255));
        this.setVisible(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
    }

    public void startGThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawTime = 1000000000/(double) FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long nowTime;

        while(gameThread!=null)
        {
            nowTime = System.nanoTime();
            delta+=(nowTime - lastTime)/drawTime;
            lastTime = nowTime;
            if(delta>=1)
            {
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update()
    {
        Pad.movePad();
        Ball.updateBallPos();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(Toolkit.getDefaultToolkit().getImage("src\\rsc\\background.png"),0,0,null);
        Pad.drawPad(g2);
        Ball.drawBall(g2);
        Field.drawBlocks(g2);
        g2.dispose();
    }
}

