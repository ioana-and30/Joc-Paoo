package monster;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Mon_Amarantha extends Entity {
    GamePanel gp;
    public Mon_Amarantha(GamePanel gp) {

        super(gp);
        this.gp=gp;
        name="Evil Queen";
        type=2;
        defaultSpeed=1;
        speed=defaultSpeed;
        maxLife=10;
        life=maxLife;
        alive=true;
        attack=3;
        defense=0;

        solidArea.x = 32;
        solidArea.y = 32;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        getAttackImage();
    }

    public void getAttackImage(){

        attackUp1 = setup("/monster/amarantha_back",gp.tileSize, gp.tileSize);
        attackUp2 = setup("/monster/amarantha_back",gp.tileSize, gp.tileSize);
        attackDown1 = setup("/monster/amarantha_fight_front",gp.tileSize, gp.tileSize);
        attackDown2 = setup("/monster/amarantha_front2",gp.tileSize, gp.tileSize);
        attackRight1 = setup("/monster/amarantha_fight_right",gp.tileSize, gp.tileSize);
        attackRight2 = setup("/monster/amarantha_left",gp.tileSize, gp.tileSize);
        attackLeft1 = setup("/monster/amarantha_fight_left",gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/monster/amarantha_right",gp.tileSize, gp.tileSize);
    }
    public void getImage(){

        up1=setup("/monster/back_amarantha",gp.tileSize,gp.tileSize);
        up2=setup("/monster/back_amarantha",gp.tileSize,gp.tileSize);
        down1=setup("/monster/front_amarantha",gp.tileSize,gp.tileSize);
        down2=setup("/monster/front_amarantha",gp.tileSize,gp.tileSize);
        right1=setup("/monster/left_amarantha",gp.tileSize,gp.tileSize);
        right2=setup("/monster/left_amarantha",gp.tileSize,gp.tileSize);
        left1=setup("/monster/right_amarantha",gp.tileSize,gp.tileSize);
        left2=setup("/monster/right_amarantha",gp.tileSize,gp.tileSize);
    }
    public void setAction() {

        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) { //every 2 seconds, select a new move ar rabdom
                Random random = new Random();
                int i = random.nextInt(100) + 1; //pick up a number from 1 to 100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void damageReaction(){

        actionLockCounter=0;
        onPath=true;
        attack();
    }
    public void update() {
        super.update();

        //we check the distance between the player and the dragons
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (onPath == false && tileDistance < 2) {
            onPath = true;
        }
        if (onPath == true && tileDistance > 5) {
            onPath = false;
        }
    }
}
