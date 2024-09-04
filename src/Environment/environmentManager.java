package Environment;

import Main.GamePanel;

import java.awt.*;

public class environmentManager {

    GamePanel gp;
    Lightning lightning;
    Lightning lightning_1;

    public environmentManager(GamePanel gp){
        this.gp=gp;
    }

    public void setup(){

        lightning=new Lightning(gp,350);
        lightning_1=new Lightning(gp,450);
    }

    public void draw(Graphics2D g2){

        if(gp.currentMap==0){
            lightning.draw(g2);
        }
        else if(gp.currentMap==1){
            lightning_1.draw(g2);
        }

    }
}
