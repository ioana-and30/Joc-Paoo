package Entity;

import Main.GamePanel;

public class Projectiles extends Entity {

    Entity user;
    public Projectiles(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){

        this.worldX=worldX;
        this.worldY=worldY;
        this.direction=direction;
        this.alive=alive;
        this.user=user;
        this.life=this.maxLife;

    }

    public void update(){

        if(user==gp.player){

            int monsterIndex=gp.checker.checkEntity(this,gp.monster);
            if(monsterIndex!=999){
                gp.player.damageMonster(monsterIndex,this,attack);
                generateParticle(user.projectile,gp.monster[gp.currentMap][monsterIndex]);
                alive=false;
            }
        }
        else{

            boolean contactPlayer=gp.checker.checkPlayer(this);
            if(gp.player.invincible==false && contactPlayer==true){
                damagePlayer(attack);
                generateParticle(user.projectile,gp.player);
                alive=false;
            }

        }
        switch(direction){
            case "up":worldY-=speed;break;
            case "down":worldY+=speed;break;
            case "left":worldX-=speed;break;
            case "right":worldX+=speed;break;
        }

        life--; // gradually losing life after you shoot a projectile, after 80 game loops
        if(life<=0){
            alive=false;
        }
        spriteCounter++;
        if(spriteCounter>12){
            if(spriteNum==1){
                spriteNum = 2;
            }
            else if(spriteNum==2){
                spriteNum=1;
            }
            spriteCounter=0;
        }
    }
}
