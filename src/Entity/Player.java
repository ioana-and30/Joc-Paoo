package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import object.Obj_Magic;
import object.Obj_Shield;
import object.Obj_Sword;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasCauldron = 0;
    public int matchCounter=0;

    // IMPORTANT
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize);
        solidArea.x = 14;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 22;

        attackArea.width = 20;
        attackArea.height = 22;

        setDefaultValues();
        getPlayerImage();
        getPLayerAttackImage();
    }
    public void setDefaultValues() {

//        // NIVEL 1
        worldX = gp.tileSize * 32;
        worldY = gp.tileSize * 34;

        // NIVEL 2
//        worldX = gp.tileSize * 24;
//        worldY = gp.tileSize * 38;

        // NIVEL 3
//        worldX = gp.tileSize * 15;
//        worldY = gp.tileSize * 28;

        defaultSpeed=5;
        speed = defaultSpeed;
        direction = "right";


        // PLAYER STATUS
        projectile=new Obj_Magic(gp);
        currentWeapon=new Obj_Sword(gp);
        currentShield=new Obj_Shield(gp);
        projectile=new Obj_Magic(gp);
        attack=getAttack(); // the total attack value is decided by strength and weapon
        defense=getDefense(); // the total defense value is decided by dexterity and shiel
        maxLife = 6;
        life = maxLife;
    }

    public void setDefaultPositions(){
        speed=4;
        direction="up";

        if(gp.currentMap==2){
            // NIVEL 1
        worldX = gp.tileSize * 32;
        worldY = gp.tileSize * 34;
        }
        else if(gp.currentMap==0){
            // NIVEL 2
        worldX = gp.tileSize * 24;
        worldY = gp.tileSize * 38;
        }
        else if(gp.currentMap==1){
            // NIVEL 3
            worldX = gp.tileSize * 15;
            worldY = gp.tileSize * 28;

        }
    }
    public void restoreLife(){
        life=maxLife;
        invincible=false;
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {

            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {image = back_feyre;}
                    if (spriteNum == 2) {image = back_2;}
                }
                if (attacking == true) {
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {image = front_feyre;}
                    if (spriteNum == 2) {image = front_2;}
                }
                if (attacking == true) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {image = left_feyre;}
                    if (spriteNum == 2) {image = left_2;}
                }
                if (attacking == true) {
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {image = right_feyre;}
                    if (spriteNum == 2) {image = right_2;}
                }
                if (attacking == true) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                break;
        }

        if (invincible == true) {

            // PLAYER GETS 75% TRANSPARENT

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        // RESET ALPHA

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void update() {

        if (attacking == true) {

            attack();
        } else if (keyH.downPressed == true || keyH.leftPressed == true || keyH.upPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {

            if (keyH.upPressed == true) {

                direction = "up";

            } else if (keyH.downPressed == true) {

                direction = "down";

            } else if (keyH.leftPressed == true) {

                direction = "left";

            } else if (keyH.rightPressed == true) {

                direction = "right";
            }

            // CHECK TILE COLLISION

            collisionOn = false;
            gp.checker.checkTile(this);

            // CHECK OBJECT COLLISION

            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

            // ATTACK

            if (gp.keyH.enterPressed == true) {

                attacking = true;
            }

            // CHECK MONSTER COLLISION

            int monsterIndex = gp.checker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK EVENT

            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
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

        if(gp.keyH.shotKeyPressed==true && projectile.alive==false
                && shotAvailableCounter==30){

            // SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX,worldY,direction,true,this);

            // CHECK VACANCY

            for(int i=0;i<gp.projectile[1].length;++i){
                if(gp.projectile[gp.currentMap][i]==null){
                    gp.projectile[gp.currentMap][i]=projectile;
                    break;
                }
            }

            shotAvailableCounter=0;
        }

        if (invincible == true) {

            invincibleCounter++;
            if (invincibleCounter > 60) {

                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter<30){
            shotAvailableCounter++;
        }

        if(life<=0){
            gp.gameState=gp.gameOverState;
        }

    }

    // GET -ERS
    public void getPlayerImage() {

        front_feyre = setup("/player/feyre_front",gp.tileSize, gp.tileSize);
        back_feyre = setup("/player/feyre_back",gp.tileSize, gp.tileSize);
        left_feyre = setup("/player/feyre_left_step",gp.tileSize, gp.tileSize);
        right_feyre = setup("/player/feyre_right_step",gp.tileSize, gp.tileSize);
        right_2 = setup("/player/right2",gp.tileSize, gp.tileSize);
        left_2 = setup("/player/left2",gp.tileSize, gp.tileSize);
        front_2 = setup("/player/front_2",gp.tileSize, gp.tileSize);
        back_2 = setup("/player/back2",gp.tileSize, gp.tileSize);
    }
    public void getPLayerAttackImage() {

        attackUp1 = setup("/player/feyre_attack_back",gp.tileSize, gp.tileSize);
        attackUp2 = setup("/player/feyre_attack_back2",gp.tileSize, gp.tileSize);
        attackDown1 = setup("/player/feyre_attack_front",gp.tileSize, gp.tileSize);
        attackDown2 = setup("/player/feyre_attack_front2",gp.tileSize, gp.tileSize);
        attackRight1 = setup("/player/feyre_attack_left",gp.tileSize, gp.tileSize);
        attackRight2 = setup("/player/feyre_attack_left2",gp.tileSize, gp.tileSize);
        attackLeft1 = setup("/player/feyre_attack_right",gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/player/feyre_attack_right2",gp.tileSize, gp.tileSize);
    }

    private int getDefense() {
        return defense=dexterity* currentShield.attackValue;
    }
    private int getAttack() {return attack=strength* currentWeapon.attackValue;}

    // MONSTER, OBJECT
    public void damageProjectile(int i) {
        if(i!=999){
            Entity projectile=gp.projectile[gp.currentMap][i];
            projectile.alive=false;
            generateParticle(projectile,projectile);
        }
    }
    public void damageMonster(int i,Entity attacker, int attack) {

        if (i != 999) {

            if (gp.monster[gp.currentMap][i].invincible == false) {

                setKnocBack(gp.monster[gp.currentMap][i],attacker);
                int damage=attack-gp.monster[gp.currentMap][i].defense;

                if(damage<0){
                    damage=0;
                }

                gp.monster[gp.currentMap][i].life -= damage;
                System.out.println("Damage!!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {

                    gp.monster[gp.currentMap][i].dying = true;
                    System.out.println("Killed the "+gp.monster[gp.currentMap][i].name);
                }
            }
        }

    }
    public void contactMonster(int i) {

        if (i != 999) {
            if (invincible == false && gp.monster[gp.currentMap][i].dying==false) {

                int damage=gp.monster[gp.currentMap][i].attack-defense;
                if(damage<0){
                    damage=0;
                }
                life -= damage;
                invincible = true;
            }

        }
    }
    public void pickUpObject(int i) {
        if (i != 999) // if we touch an object
        {
            String objectName = gp.obj[gp.currentMap][i].name;

            switch (objectName) {
                case "mushroom":
                    break;
                case "cauldron":
                    life+=1;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "wings":
                    gp.sound_effect(1);
                    life=maxLife;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "door":
                    gp.ui.gameFinished = true;
                    gp.stop_music();
                    break;
                case "cazan":
                    matchCounter++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "zana":
                    matchCounter++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "blazon":
                    matchCounter++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "dragon":
                    matchCounter++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "crown":
                    gp.obj[gp.currentMap][i]=null;
                    gp.ui.gameFinished=true;
            }
            System.out.println(hasCauldron);
            System.out.println(matchCounter);
        }
    }
}