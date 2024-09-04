package Entity;

import Main.GamePanel;
import Main.UtilityTool;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Entity { // CLASA ABSTRACTA

    GamePanel gp;
    public BufferedImage back_feyre,front_feyre,left_feyre,right_feyre,front_2,right_2,left_2,back_2;
    public BufferedImage down1,up1,up2,down2,left1,left2,right1,right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackRight1, attackRight2, attackLeft1, attackLeft2;
    public Rectangle hitbox=new Rectangle(0,0,0,0);
    public Rectangle attackArea=new Rectangle(0,0,0,0);
    public Rectangle solidArea=new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[]=new String[20];
    public BufferedImage image,image2,image3;
    public Entity attacker;

    // STATE
    public String direction="down";
    public int worldX,worldY; // player position on the world map
    public int spriteNum=1;
    public boolean collisionOn=false;
    public boolean collision=false;
    public boolean invincible=false;
    public boolean hpBarOn=false;
    public boolean onPath=false;
    public boolean knockback=false;
    public String knockBackDirectionn;
    public boolean attacking = false;


    // CHARACTER ATRIBUTES

    public int maxLife, life, speed;
    public String name;
    public int type; // 1=player,2=monster
    public boolean alive=true;
    public boolean dying=false;
    public int attack;
    public int defense;
    public int level;
    public int strength;
    public int dexterity;
    public int exp;
    public int nextLevelExp;
    public int mana;
    public int defaultSpeed;
    public Projectiles projectile;
    public Entity currentWeapon;
    public Entity currentShield;
    public int type_monster=2;
    public int motion1_duration;
    public int motion2_duration;

    // ITEM ATTRIBUTES

    public int attackValue;
    public int defenseValue;
    public int useCost;

    // COUNTERS

    public int spriteCounter=0;
    public int actionLockCounter=0;
    public int invincibleCounter=0;
    public int dyingCounter=0;
    public int hpBarCounter=0;
    public int shotAvailableCounter=0;
    public int knockBackCounter=0;

    // IMPORTANT

    public Entity(GamePanel gp) {
        this.gp=gp;
    }

    public BufferedImage setup(String imagePath, int width, int height)
    {
        UtilityTool uTool=new UtilityTool();
        BufferedImage image= null;

        try{
            image= ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image=uTool.scaleImage(image,width,height);

        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        return image;
    }
    public void draw(Graphics2D g2)
    {

        BufferedImage image=null;
        int screenX=worldX-gp.player.worldX+gp.player.screenX;
        int screenY=worldY-gp.player.worldY+gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX-gp.tileSize < gp.player.worldX + gp.player.screenX  &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            int tempScreenX=screenX;
            int tempScreenY=screenY;

            switch (direction) {

                case "up":
                    if (attacking == false) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    }
                    if (attacking == true) {
                        tempScreenY=screenY-gp.tileSize;
                        if (spriteNum == 1) {image = attackUp1;}
                        if (spriteNum == 2) {image = attackUp2;}
                    }
                    break;
                case "down":
                    if (attacking == false) {
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                    }
                    if (attacking == true) {
                        if (spriteNum == 1) {image = attackDown1;}
                        if (spriteNum == 2) {image = attackDown2;}
                    }
                    break;
                case "left":
                    if (attacking == false) {
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                    }
                    if (attacking == true) {
                        tempScreenX=screenX-gp.tileSize;
                        if (spriteNum == 1) {image = attackLeft1;}
                        if (spriteNum == 2) {image = attackLeft2;}
                    }
                    break;
                case "right":
                    if (attacking == false) {
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                    }
                    if (attacking == true) {
                        if (spriteNum == 1) {image = attackRight1;}
                        if (spriteNum == 2) {image = attackRight2;}
                    }
                    break;
            }

            // MONSTER HP BAR

            if(type==2 && hpBarOn==true){

                double oneScale=(double)gp.tileSize/maxLife; // length of 1 hp depend on the rectangle
                double hpBarValue=oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)hpBarValue,10);

                hpBarCounter++;

                if(hpBarCounter>600){ // after 10 seconds the bar disapears, if the player is not attacking

                    hpBarCounter=0;
                    hpBarOn=false;
                }
            }

            if(invincible==true){

                hpBarOn=true;
                hpBarCounter=0;
                changeAlpha(g2,0.4f);
            }

            if(dying==true){
                dyingAnimation(g2);}

            g2.drawImage(image, tempScreenX ,tempScreenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2,1f);
        }

    }
    public void update(){

        if(attacking==true){
            attack();
        }
        else{
            setAction();
            checkCollision();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn==false) {
                switch (direction) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 8) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if(invincible==true){

            invincibleCounter++;
            if(invincibleCounter>40){

                invincible=false;
                invincibleCounter=0;
            }
        }

        if(shotAvailableCounter<30){
            shotAvailableCounter++;
        }
    }

    // GET -ERS
    public int getXDistance(Entity target){
        int xDistance = Math.abs(worldX - target.worldX);
        return xDistance;
    }
    public int getYDistance(Entity target){
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }
    public int getTileDistance(Entity target){
        int tileDistance = (getXDistance(target) + getYDistance(target)) / gp.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }
    public void getRandomDirection(){

        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            // AI KINDA

            if (i <= 25) {direction = "up";}
            else if (i > 25 && i <= 50) {direction = "down";}
            else if (i > 50 && i <= 75) {direction = "left";}
            else if (i > 75 && i <= 100) {direction = "right";}

            actionLockCounter = 0;
        }
    }
    public Color getParticleColor(){
        Color color=null;
        return color;
    }
    public int getParticleSize(){
        int size=0; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed=0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife=0;
        return maxLife;
    }

    // ANIMATION, DAMAGE, ACTION, ATTACK, PATH
    public void generateParticle(Entity generator, Entity target){

        Color color=generator.getParticleColor();
        int size=generator.getParticleSize();
        int speed=generator.getParticleSpeed();
        int maxLife=generator.getParticleMaxLife();

        Particle p1=new Particle(gp,generator,color,size,speed,maxLife,-2,-1);
        Particle p2=new Particle(gp,generator,color,size,speed,maxLife,2,-1);
        Particle p3=new Particle(gp,generator,color,size,speed,maxLife,-2,1);
        Particle p4=new Particle(gp,generator,color,size,speed,maxLife,2,1);

        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i=5;

        if(dyingCounter<=i){changeAlpha(g2,0f);}
        if(dyingCounter>i && dyingCounter<=i*2){changeAlpha(g2,1f);} // in loc de changeAlpha pot sa schimb sprite urile, ca sa arate mai bine
        if(dyingCounter>i*2 && dyingCounter<=i*3){changeAlpha(g2,0f);}
        if(dyingCounter>i*3 && dyingCounter<=i*4){changeAlpha(g2,1f);}
        if(dyingCounter>i*4 && dyingCounter<=i*5){changeAlpha(g2,0f);}
        if(dyingCounter>i*5 && dyingCounter<=i*6){changeAlpha(g2,1f);}
        if(dyingCounter>i*6 && dyingCounter<=i*7){changeAlpha(g2,0f);}
        if(dyingCounter>i*7 && dyingCounter<=i*8){changeAlpha(g2,1f);}
        if(dyingCounter>i*8){alive=false;}
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }
    public void setAction(){}
    public  void damageReaction(){}
    public void attack() {

        spriteCounter++;

        // afisez prima imagine pt 5 frame uri
        if (spriteCounter <= motion1_duration) {

            spriteNum = 1;
        }

        // afisez a 2a imagine pt 6-25 frame uri
        if (spriteCounter > motion1_duration && spriteCounter < motion2_duration) {

            spriteNum = 2;

            // Save the current worldX, worldY, solidArea

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player`s worldX/Y for the attackArea

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // AttackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type==type_monster){
                if(gp.checker.checkPlayer(this)==true){
                    damagePlayer(attackValue);
                }
            }
            else{ // PLAYER

                // Check monster collision with the updated worldX/Y and solidArea
                int monsterIndex = gp.checker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,this,attack);

                int projectileIndex=gp.checker.checkEntity(this,gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }


            // After checking collision restore the original data

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damagePlayer(int attack){

        if(gp.player.invincible==false){

            int damage=attack-gp.player.defense;
            if(damage<0){
                damage=0;
            }
            gp.player.life-=damage;
            gp.player.invincible=true;
        }
    }
    public void setKnocBack(Entity target,Entity attacker){

        this.attacker=attacker;
        target.knockBackDirectionn=attacker.direction;
        target.speed+=10;
        target.knockback=true;
    }
    public void searchPath(int goalCol, int goalRow){

        // Where this entity is
        int startCol=(worldX+solidArea.x)/gp.tileSize;
        int startRow=(worldY+solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);
        if(gp.pFinder.search()==true){

            // Next worldX and worldY
            int nextX=gp.pFinder.pathList.get(0).col*gp.tileSize;
            int nextY=gp.pFinder.pathList.get(0).row*gp.tileSize;

            // Entity`s solid area position
            int entLeftX=worldX+solidArea.x;
            int entRightX=worldX+solidArea.x+solidArea.width;
            int entTopY=worldY+solidArea.y;
            int entBottomY=worldY+solidArea.y+solidArea.height;

            if(entTopY>nextY && entLeftX>=nextX && entRightX<nextX+gp.tileSize){
                direction="up";
            }
            else if(entTopY<nextY && entLeftX>=nextX && entRightX<nextX+gp.tileSize){
                direction="down";
            }
            else if(entTopY>=nextY && entBottomY<nextY +gp.tileSize){
                // Left or right
                if(entLeftX>nextX){
                    direction="left";
                }
                if(entLeftX<nextX){
                    direction="right";
                }
            }
            else if(entTopY>nextY && entLeftX>nextX){
                // Up or left
                direction="up";
                checkCollision();
                if(collisionOn==true){
                    direction="left";
                }
            }
            else if(entTopY>nextY && entLeftX<nextX){
                // Up or right
                direction="up";
                checkCollision();
                if(collisionOn==true){
                    direction="right";
                }
            }
            else if(entTopY<nextY && entLeftX>nextX){
                // Down or left
                direction="down";
                checkCollision();
                if(collisionOn==true){
                    direction="left";
                }
            }
            else if(entTopY<nextY && entLeftX<nextX){
                // Down or right
                direction="down";
                checkCollision();
                if(collisionOn==true){
                    direction="right";
                }
            }
//            // If reaches the goal, stop the search
//            int nextCol=gp.pFinder.pathList.get(0).col;
//            int nextRow=gp.pFinder.pathList.get(0).row;
//            if(nextCol==goalCol && nextRow==goalRow){
//                onPath=false;
//            }
        }
    }

    // CHECK -ERS
    public void checkStopChasingOrNot(Entity target, int distance, int rate){
        if(getTileDistance(target)>distance){
            int i=new Random().nextInt(rate);
            if(i==0){
                onPath=false;
            }
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate){
        if(getTileDistance(target)<distance){
            int i=new Random().nextInt(rate);
            if(i==0){
                onPath=true;
            }
        }
    }
    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {

            // CHECK VACANCY

            for (int ii = 0; ii < gp.projectile[1].length; ++ii) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void checkCollision(){

        collisionOn=false;
        gp.checker.checkTile(this);
        gp.checker.checkObject(this,false);
        gp.checker.checkEntity(this,gp.monster);
        boolean contactPlayer= gp.checker.checkPlayer(this);
        // IF PLAYER MAKES CONTACT WITH A MONSTER

        if(this.type==2 && contactPlayer==true){

            damagePlayer(attack);
        }


    }
    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange=false;
        int xDis=getXDistance(gp.player);
        int yDis=getYDistance(gp.player);

        switch(direction){
            case "up":
                if(gp.player.worldY<worldY && yDis < straight && xDis < horizontal){
                    targetInRange=true;
                }
                break;
            case "down":
                if(gp.player.worldY>worldY && yDis < straight && xDis < horizontal){
                    targetInRange=true;
                }
                break;
            case "left":
                if(gp.player.worldX<worldX && xDis < straight && yDis < horizontal){
                    targetInRange=true;
                }
                break;
            case "right":
                if(gp.player.worldX>worldX && xDis < straight && yDis < horizontal){
                    targetInRange=true;
                }
                break;
        }
        if(targetInRange==true){
            //Check if it initiates an attack
            int i =new Random().nextInt(rate);
            if(i==0){
                attacking=true;
                spriteNum=1;
                spriteCounter=0;
                shotAvailableCounter=0;
            }
        }
    }
}
