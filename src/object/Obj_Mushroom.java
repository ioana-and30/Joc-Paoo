package object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Mushroom extends Entity {
    public Obj_Mushroom(GamePanel gp)
    {
        super(gp);
        name="mushroom";
        down1=setup("/objects/mushroom",gp.tileSize,gp.tileSize);
        collision=true;

    }
}
