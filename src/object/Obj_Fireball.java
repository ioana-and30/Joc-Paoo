package object;

import Entity.Entity;
import Entity.Projectiles;
import Main.GamePanel;

import java.awt.*;

public class Obj_Fireball extends Projectiles {

    GamePanel gp;
    public Obj_Fireball(GamePanel gp) {
        super(gp);
        this.gp=gp;

        name="Fireball";
        speed=8;
        maxLife=50;
        life=maxLife;
        attack=2;
        useCost=1;  // ypu spend 1 mana to cast this spell
        alive=false;
        getImage();
    }

    public void getImage(){

        up1=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        up2=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        down1=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        down2=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        left1=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        left2=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        right1=setup("/projectile/fireball",gp.tileSize,gp.tileSize);
        right2=setup("/projectile/fireball",gp.tileSize,gp.tileSize);

    }
    public int getParticleSize(){
        int size=10; // 6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed=1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife=20;
        return maxLife;
    }
}
