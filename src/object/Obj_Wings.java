package object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Wings extends Entity {

    public Obj_Wings(GamePanel gp)
    {
        super(gp);
        name="wings";
        down1=setup("/objects/wings",gp.tileSize,gp.tileSize);
        collision=true;

    }
}
