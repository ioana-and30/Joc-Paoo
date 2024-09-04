package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Dragon extends Entity {
    public Obj_Dragon(GamePanel gp) {
        super(gp);
            name="dragon";
            down1=setup("/objects/dragon",gp.tileSize,gp.tileSize);
    }
}
