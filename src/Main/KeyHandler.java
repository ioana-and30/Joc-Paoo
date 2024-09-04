package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed,shotKeyPressed,healingKeyPressed;
    boolean checkDrawTime=false;
    public KeyHandler(GamePanel gp)
    {
        this.gp=gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int code =e.getKeyCode(); // return the number of the key that was pressed

            // TITLE STATE
            if (gp.gameState == gp.titleState) {
                if (code == KeyEvent.VK_W) {

                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S) {

                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0;
                    }
                }

                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.gameState = gp.playState;
                        gp.play_music(0);
                    }
                    if (gp.ui.commandNum == 1) {
                        //nothing now
                    }
                    if (gp.ui.commandNum == 2) {
                        System.exit(0);
                    }
                }
            }

        //GAME OVER STATE

        if(gp.gameState == gp.gameOverState){
            if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;

                }
            }
            if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }

            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState=gp.playState;
                    try {
                        gp.retry();
                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
                    }
                    //gp.playMusic(0);
                }
                else if(gp.ui.commandNum == 1){
                    gp.gameState=gp.titleState;
                    try {
                        gp.restart();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        // PLAY STATE
        else if(gp.gameState==gp.playState)
        {
            if (code == KeyEvent.VK_W)
            {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {

                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {

                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {

                rightPressed = true;
            }
            if(code==KeyEvent.VK_ENTER){

                enterPressed=true;
            }
            if (code == KeyEvent.VK_F) {
                shotKeyPressed=true;
            }
            if(code==KeyEvent.VK_H){
                healingKeyPressed=true;
            }
        }

        // PAUSE STATE

        else if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            } else {
                gp.gameState = gp.pauseState;
            }
        }

        if (gp.gameState == gp.pauseState) {

            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum-=2;
                if(gp.ui.commandNum<0)
                {
                    gp.ui.commandNum=2;
                }

            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum+=2;
                if(gp.ui.commandNum>2)
                {
                    gp.ui.commandNum=0;
                }
            }

            if(code==KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum==0)
                {
                    gp.gameState=gp.playState;
                    gp.play_music(0);
                }

                if(gp.ui.commandNum==2)
                {
                    gp.gameState=gp.titleState;
                    // System.exit(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code=e.getKeyCode();

        if(code == KeyEvent.VK_W) {

            upPressed=false;
        }
        if(code == KeyEvent.VK_S) {

            downPressed=false;
        }
        if(code == KeyEvent.VK_A) {

            leftPressed=false;
        }
        if(code == KeyEvent.VK_D) {

            rightPressed=false;
        }
        if(code==KeyEvent.VK_ENTER){

            enterPressed=false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed=false;
        }
        if(code==KeyEvent.VK_H){
            healingKeyPressed=false;
        }
    }
}
