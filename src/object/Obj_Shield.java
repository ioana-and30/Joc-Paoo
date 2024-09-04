package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Shield extends Entity {
    public Obj_Shield(GamePanel gp) {
        super(gp);

        name="Shield";
        down1=setup("/objects/shield",gp.tileSize,gp.tileSize);
        defenseValue=2;
    }
}
