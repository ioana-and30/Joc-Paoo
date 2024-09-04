package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Star extends Entity {
    public Obj_Star(GamePanel gp) {
        super(gp);

        name="star";
        image=setup("/objects/star",gp.tileSize*2,gp.tileSize*2);
    }
}
