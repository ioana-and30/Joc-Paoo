package object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Cauldron extends Entity {
    public Obj_Cauldron(GamePanel gp)
    {
        super(gp);
        name="cauldron";
        image=setup("/objects/cauldron",gp.tileSize,gp.tileSize);

    }
}
