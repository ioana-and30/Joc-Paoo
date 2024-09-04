package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Cazan extends Entity {
    public Obj_Cazan(GamePanel gp) {
        super(gp);

        name="cazan";
        down1=setup("/objects/cazan",gp.tileSize,gp.tileSize);
    }
}
