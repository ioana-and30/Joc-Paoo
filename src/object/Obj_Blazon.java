package object;

import Entity.Entity;
import Main.GamePanel;

public class Obj_Blazon extends Entity {
    public Obj_Blazon(GamePanel gp) {
        super(gp);

        name="blazon";
        down1=setup("/objects/blazon",gp.tileSize,gp.tileSize);
    }
}
