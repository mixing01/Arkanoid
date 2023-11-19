package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_LEFT)
        {
            Pad.setMovePadLeft(true);
        }
        if(code==KeyEvent.VK_RIGHT)
        {
            Pad.setMovePadRight(true);
        }
        if(code==KeyEvent.VK_SPACE)
        {
            Ball.setIsBallStatic(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_LEFT)
        {
            Pad.setMovePadLeft(false);
        }
        if(code==KeyEvent.VK_RIGHT)
        {
            Pad.setMovePadRight(false);
        }
    }
}
