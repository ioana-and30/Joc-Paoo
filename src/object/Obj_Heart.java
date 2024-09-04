package object;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Heart extends Entity {
    public Obj_Heart(GamePanel gp)
    {
        super(gp);
        name="heart";

        image=setup("/objects/heart_full",gp.tileSize,gp.tileSize);
        image2=setup("/objects/heart_half",gp.tileSize,gp.tileSize);
        image3=setup("/objects/heart_blank",gp.tileSize,gp.tileSize);

    }
}
