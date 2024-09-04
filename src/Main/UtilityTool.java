package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height)
    {
        BufferedImage scaledImage=new BufferedImage(width,height,original.getType());
        Graphics2D g2=scaledImage.createGraphics(); // creates a Graphics2D, which can be used to draw into this
        g2.drawImage(original,0,0,width,height,null);// BufferedImage
        g2.dispose();

        return scaledImage;
    }

}
