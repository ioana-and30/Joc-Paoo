package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Sword extends Entity {
    public Obj_Sword(GamePanel gp) {
        super(gp);

        name="Sword";
        down1=setup("/objects/sword",gp.tileSize,gp.tileSize);
        attackValue=2;
    }
}
