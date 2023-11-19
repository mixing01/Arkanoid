package game;

import java.awt.*;
import java.util.ArrayList;

import static game.Pad.*;

public class Ball
{
    private static final int[] lastTouched = {-1, -1};
    private static boolean isBallStatic = true;
    private static boolean killBall = false;
    private static int padX = Pad.getPadX();
    private static final int padY = Pad.getPadY();
    private static final int padWidth = Pad.getPadWidth();
    private static final int screenWidth = GamePanel.getScreenWidth();
    private static final int ballD = 30;
    private static int ballSpeedUp = 7;
    private static int ballSpeedDown = 0;
    private static int ballSpeedLeft = 0;
    private static int ballSpeedRight = 0;
    private static final int maxSpeed = 7;
    static int ballX = padX+padWidth/2 - ballD/2;
    static int ballY = padY-ballD;
    static Color ball_color = new Color(204, 255, 103, 255);

    public static void setIsBallStatic(boolean isBallStatic) {
        Ball.isBallStatic = isBallStatic;
    }
    public static void drawBall(Graphics2D g2) {
        g2.setColor(ball_color);
        g2.fillOval(ballX,ballY, ballD, ballD);
    }
    public static void handleBoinkPad()
    {
        if(padX<=ballX+ballD && padX+padWidth>=ballX && ballY+ballD+ballSpeedDown>=padY)
        {
            lastTouched[0] = -1;
            lastTouched[1] = -1;
            int middle = padX + padWidth/2;
            double angle = ((double)ballX+(double)ballD/2 - (double)middle)/(double)padWidth; //length from the middle of the pad to the ballX
            if(((int) Math.round(angle*maxSpeed))==0)
            {
                if(ballSpeedLeft>0)
                {
                    ballSpeedLeft = 1;
                }
                else if (ballSpeedRight>0){
                    ballSpeedRight = 1;
                }
                ballSpeedUp = maxSpeed;
                ballSpeedDown = 0;
            }
            else {
                if (angle < 0) {
                    angle = angle * (-1);
                    ballSpeedLeft = (int) Math.round(angle * maxSpeed);
                    ballSpeedUp = maxSpeed;
                    ballSpeedDown = 0;
                    ballSpeedRight = 0;
                } else {
                    ballSpeedRight = (int) Math.round(angle * maxSpeed);
                    ballSpeedUp = maxSpeed;
                    ballSpeedDown = 0;
                    ballSpeedLeft = 0;
                }
            }
        }
        else if((padX>ballX+ballD || padX+padWidth<ballX) && ballY+ballD+ballSpeedDown>=padY)
        {
            lastTouched[0] = -1;
            lastTouched[1] = -1;
            killBall = true;
        }
    }

    private static void handleBoinkWalls()
    {
        if(ballY<=0)
        {
            ballSpeedDown = ballSpeedUp;
            ballSpeedUp = 0;
            lastTouched[0] = -1;
            lastTouched[1] = -1;
        }
        if(ballX<=0)
        {
            ballSpeedRight = ballSpeedLeft;
            ballSpeedLeft = 0;
            lastTouched[0] = -1;
            lastTouched[1] = -1;
        }
        else if(ballX+ballD>=screenWidth)
        {
            ballSpeedLeft = ballSpeedRight;
            ballSpeedRight = 0;
            lastTouched[0] = -1;
            lastTouched[1] = -1;
        }
    }

    private static void handleBlockRepulse(int i, int j, ArrayList<ArrayList<Block>> blockArray, int ballCentreX, int ballCentreY)
    {
        int blockPosX = blockArray.get(i).get(j).getPosX();
        int blockPosY = blockArray.get(i).get(j).getPosY();
        int blockSize = Block.getSize();
        int temp;
        if(ballCentreX<blockPosX)
        {
            if((ballCentreY<blockPosY) || (ballCentreY>(blockPosY+blockSize)))
            {

                temp = ballSpeedUp;
                ballSpeedUp = ballSpeedDown;
                ballSpeedDown = temp;

            }
            temp = ballSpeedRight;
            ballSpeedRight = ballSpeedLeft;
            ballSpeedLeft = temp;
            if(ballSpeedRight>0)
            {
                ballSpeedRight+=Math.random()*(3)-1;
            }
            else
            {
                ballSpeedLeft+=Math.random()*(3)-1;
            }
        }
        else if(ballCentreX<=(blockPosX+blockSize))
        {
            temp = ballSpeedUp;
            ballSpeedUp = ballSpeedDown;
            ballSpeedDown = temp;
        }
        else
        {
            if((ballCentreY<blockPosY) || (ballCentreY>(blockPosY+blockSize)))
            {

                temp = ballSpeedUp;
                ballSpeedUp = ballSpeedDown;
                ballSpeedDown = temp;

            }
            temp = ballSpeedRight;
            ballSpeedRight = ballSpeedLeft;
            ballSpeedLeft = temp;
            if(ballSpeedRight>0)
            {
                ballSpeedRight+=Math.random()*(3)-1;
            }
            else
            {
                ballSpeedLeft+=Math.random()*(3)-1;
            }
        }
    }
    private static void handleBoinkBlocks()
    {
        int[] coordinates;
        int ballR = ballD/2;
        int ballCentreX = ballX + ballR;
        int ballCentreY = ballY + ballR;
        ArrayList<ArrayList<Block>> blockArray = Field.getBlocksArray();
        for(int x = ballX; x <=ballX + ballD; x++)
        {
            for(int y = ballY; y <=ballY+ballD; y++)
            {
                if(Math.pow(x-ballCentreX,2)+Math.pow(y-ballCentreY,2)<=Math.pow(ballR,2))
                {
                    coordinates = Field.isInsideBlock(x, y);
                    if (coordinates[0] >= 0) {
                        if(blockArray.get(coordinates[0]).get(coordinates[1]).getHp()>0) {
                            if(lastTouched[0]!=coordinates[0] && lastTouched[1]!=coordinates[1])
                            {
                                lastTouched[0] = coordinates[0];
                                lastTouched[1] = coordinates[1];
                                handleBlockRepulse(coordinates[0], coordinates[1], blockArray, ballCentreX, ballCentreY);
                                Field.handleCollision(coordinates[0], coordinates[1]);
                            }
                        }
                    }
                }
            }
        }

    }
    public static void updateBallPos()
    {
        padX = getPadX();
        if(isBallStatic) {
            ballX = padX+padWidth/2 - ballD/2;
            ballY = padY-ballD;
        }
        else {
            if (killBall) {
                ballY = ballY + ballSpeedDown;
                ballX = ballX + ballSpeedRight - ballSpeedLeft;
                handleBoinkWalls();
                if (ballY + ballSpeedDown >= screenWidth) {
                    killBall = false;
                    isBallStatic = true;
                }
            } else {
                handleBoinkPad();
                handleBoinkWalls();
                handleBoinkBlocks();
                ballY = ballY - ballSpeedUp + ballSpeedDown;
                ballX = ballX - ballSpeedLeft + ballSpeedRight;
            }
        }

    }
}
