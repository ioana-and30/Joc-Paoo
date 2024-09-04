package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Crown extends Entity {
    public Obj_Crown(GamePanel gp) {
        super(gp);

        name="crown";
        down1=setup("/objects/crown",gp.tileSize,gp.tileSize);
    }
}
