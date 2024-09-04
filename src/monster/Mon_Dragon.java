package monster;


import Entity.Entity;
import Main.GamePanel;
import object.Obj_Fireball;
import java.io.IOException;
import java.util.Random;

public class Mon_Dragon extends Entity {

    GamePanel gp;

    public Mon_Dragon(GamePanel gp) throws IOException {
        super(gp);
        this.gp = gp;
        type = 2;
        name = "dragon";
        speed = 2;
        maxLife = 8;
        life = maxLife;
        attack = 1;

        projectile = new Obj_Fireball(gp);

        solidArea.x = 3;
        solidArea.y = 3;
        solidArea.width = 42;
        solidArea.height = 52;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void update() {
        super.update();

        //we check the distance between the player and the dragons
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (onPath == false && tileDistance < 1) {
            onPath = true;
        }
        if (onPath == true && tileDistance > 3) {
            onPath = false;
        }
    }

    public void getImage() {

        up1 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/dragon", gp.tileSize, gp.tileSize);
    }

    public void damageReaction() {

        actionLockCounter = 0;
        onPath = true;
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
            int i = new Random().nextInt(100) + 1;
            if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);
                for (int j = 0; j < gp.projectile[1].length; ++j) {
                    if (gp.projectile[gp.currentMap][j] == null) {
                        gp.projectile[gp.currentMap][j] = projectile;
                        break;
                    }
                    shotAvailableCounter = 0;
                }
            }
        }
    }
}
