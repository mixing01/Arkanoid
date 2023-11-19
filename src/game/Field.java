package game;

import java.awt.*;
import java.util.ArrayList;

public class Field
{
    private static final int screenWidth = GamePanel.getScreenWidth();
    private static final int size = Block.getSize();
    private static final int margin = 10;
    private static final int screenHeight = GamePanel.getScreenHeight();
    private static final int rowCount = ((screenHeight/2)-margin)/(size+margin);
    private static final int colCount = (screenWidth-margin)/(size+margin);
    private static final int beginX = (screenWidth-(colCount*(size+margin)-margin))/2;
    private static final int beginY = (screenHeight/2-(rowCount*(size+margin)+margin))/2;
    private static ArrayList<ArrayList<Block>> blocksArray = new ArrayList<>();
    public static int getMargin(){
        return margin;
    }

    public static int getRowCount() {
        return rowCount;
    }

    public static int getColCount() {
        return colCount;
    }

    public static ArrayList<ArrayList<Block>> getBlocksArray() {
        return blocksArray;
    }

    public static void handleCollision(int i, int j) {
        blocksArray.get(i).get(j).handleCollision();
    }

    public static int[] isInsideBlock(int x, int y)
    {
        int[] coordinates = new int[]{-1, -1};
        for(int i = 0; i<rowCount;i++)
        {
            if((y>=blocksArray.get(i).get(0).getPosY()) && (y<=blocksArray.get(i).get(0).getPosY()+size))
            {
                for(int j = 0; j<colCount; j++)
                {
                    if((x>=blocksArray.get(i).get(j).getPosX()) && (x<=blocksArray.get(i).get(j).getPosX()+size))
                    {
                        coordinates[0] = i;
                        coordinates[1] = j;
                    }
                }
            }
        }
        return coordinates;
    }

    public static void initBlocks()
    {
        for(int i = 0; i<rowCount; i++)
        {
            ArrayList<Block> temp = new ArrayList<>();
            for(int j = 0; j<colCount; j++)
            {
                int tempHp = (int) (Math.random()*(4-1)+1);
                temp.add(new Block(tempHp, beginX+j*(size+margin),beginY+i*(size+margin)));
            }
            blocksArray.add(temp);
        }
    }
    public static void drawBlocks(Graphics2D g2)
    {
        int tempHp;
        int tempPosX;
        int tempPosY;
        for(int i = 0; i<blocksArray.size();i++)
        {
            for(int j = 0; j<blocksArray.get(i).size();j++)
            {
                Block temp = blocksArray.get(i).get(j);
                tempHp = temp.getHp();
                tempPosX = temp.getPosX();
                tempPosY = temp.getPosY();
                if(tempHp==1)
                {
                    g2.setColor(new Color(255, 0, 0));
                    g2.fillRect(tempPosX, tempPosY, size, size);
                }
                else if(tempHp==2)
                {
                    g2.setColor(new Color(255, 187, 24));
                    g2.fillRect(tempPosX, tempPosY, size, size);
                }
                else if(tempHp==3)
                {
                    g2.setColor(new Color(16, 255, 0));
                    g2.fillRect(tempPosX, tempPosY, size, size);
                }
            }
        }
    }
}
