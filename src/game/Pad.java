package game;

import java.awt.*;


public class Pad
{
    private static boolean movePadLeft = false;
    private static boolean movePadRight = false;
    private static final int screenWidth = GamePanel.getScreenWidth();
    private static final int padWidth = 250;
    private static final int padHeight = 30;
    private static final int padY = 620;
    private static int padX = screenWidth/2 - padWidth/2;
    private static final int brimWidth = 20;
    private static final int padSpeed = 10;
    private static final Color pad_color = new Color(255, 0, 0, 255);
    private static final Color brim_color = new Color(255, 73, 253);

    public static int getPadX() {
        return padX;
    }

    public static int getPadY() {
        return padY;
    }

    public static int getPadWidth() {
        return padWidth;
    }

    public static int getPadHeight() {
        return padHeight;
    }

    public static void setMovePadLeft(boolean movePadLeft) {
        Pad.movePadLeft = movePadLeft;
    }

    public static void setMovePadRight(boolean movePadRight) {
        Pad.movePadRight = movePadRight;
    }

    public static void movePadLeft() {
        if (padX >= padSpeed) {
            padX -= padSpeed;
        } else {
            padX = 0;
        }
    }
    public static void movePadRight()
    {
        if(padX<=screenWidth-padWidth-padSpeed) {
            padX+=padSpeed;
        } else {
            padX = screenWidth-padWidth;
        }
    }
    public static void movePad()
    {
        if(movePadRight){
            movePadRight();
        } else if (movePadLeft) {
            movePadLeft();
        }
    }
    public static void drawPad(Graphics2D g2) {
        g2.setColor(pad_color);
        g2.fillRect(padX, padY, padWidth, padHeight);
        g2.setColor(brim_color);
        g2.fillRect(padX, padY, brimWidth, padHeight);
        g2.fillRect(padX+padWidth-brimWidth, padY, brimWidth, padHeight);
    }
}
