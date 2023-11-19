package game;

import java.util.ArrayList;

public class Block
{
    private int hp;
    private int posX, posY;
    private static final int size = 50;

    public static int getSize()
    {
        return size;
    }
    public int getHp(){
        return hp;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY()
    {
        return posY;
    }

    Block(int hp, int posX, int posY)
    {
        this.hp = hp;
        this.posX = posX;
        this.posY = posY;
    }

    public void handleCollision()
    {
        if(this.hp>0)
        {
            this.hp--;
        }
    }
}
