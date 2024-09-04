package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Zana extends Entity {
    public Obj_Zana(GamePanel gp) {
        super(gp);

        name="zana";
        down1=setup("/objects/zana",gp.tileSize,gp.tileSize);
    }
}
