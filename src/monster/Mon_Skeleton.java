package monster;

import Entity.Entity;
import Main.GamePanel;
import object.Obj_Fireball;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Mon_Skeleton extends Entity {

    GamePanel gp;
    public Mon_Skeleton(GamePanel gp) {

        super(gp);
        this.gp=gp;
        name="skeleton";
        type=2;
        defaultSpeed=1;
        speed=defaultSpeed;
        maxLife=6;
        life=maxLife;
        alive=true;
        attack=2;
        defense=0;

        solidArea.x = 3;
        solidArea.y =3;
        solidArea.width = 42;
        solidArea.height = 52;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        getAttackImage();
    }
    public void getAttackImage(){

        attackUp1 = setup("/monster/skeletonlord_attack_up_1",gp.tileSize, gp.tileSize);
        attackUp2 = setup("/monster/skeletonlord_attack_up_2",gp.tileSize, gp.tileSize);
        attackDown1 = setup("/monster/skeletonlord_attack_down_1",gp.tileSize, gp.tileSize);
        attackDown2 = setup("/monster/skeletonlord_attack_down_2",gp.tileSize, gp.tileSize);
        attackRight1 = setup("/monster/skeletonlord_attack_right_1",gp.tileSize, gp.tileSize);
        attackRight2 = setup("/monster/skeletonlord_attack_right_2",gp.tileSize, gp.tileSize);
        attackLeft1 = setup("/monster/skeletonlord_attack_left_1",gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/monster/skeletonlord_attack_left_2",gp.tileSize, gp.tileSize);
    }
    public void getImage(){

        up1=setup("/monster/skeletonlord_up_1",gp.tileSize,gp.tileSize);
        up2=setup("/monster/skeletonlord_up_2",gp.tileSize,gp.tileSize);
        down1=setup("/monster/skeletonlord_down_1",gp.tileSize,gp.tileSize);
        down2=setup("/monster/skeletonlord_down_2",gp.tileSize,gp.tileSize);
        right1=setup("/monster/skeletonlord_right_1",gp.tileSize,gp.tileSize);
        right2=setup("/monster/skeletonlord_right_2",gp.tileSize,gp.tileSize);
        left1=setup("/monster/skeletonlord_left_1",gp.tileSize,gp.tileSize);
        left2=setup("/monster/skeletonlord_left_2",gp.tileSize,gp.tileSize);
    }
    public void setAction() {

        if(onPath == true){
            int goalCol=(gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow=(gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);
        }
        else {
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
    }
    public void update(){

        super.update();

        //we check the distance between the player and the dragons

        int xDistance=Math.abs(worldX-gp.player.worldX);
        int yDistance=Math.abs(worldY-gp.player.worldY);
        int tileDistance=(xDistance + yDistance)/gp.tileSize;

        if(onPath == false && tileDistance <2){
            onPath = true;
        }
        if(onPath == true && tileDistance >4){
            onPath=false;
        }
    }
}
