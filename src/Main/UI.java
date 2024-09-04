package Main;

import Entity.Entity;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// handle all the on-screen UI, display text / item icons
public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, arial_40;
    BufferedImage heart_full, heart_half,heart_blank;
    BufferedImage cauldronImage,starImage;
    BufferedImage backgroundImage=null;
    BufferedImage pauseImage=null;

    public boolean gameFinished=false;
    public int commandNum=0;
    public UI(GamePanel gp) {
        this.gp=gp;
        arial_40=new Font("Arial", Font.PLAIN,40);

        InputStream is=getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
        try {
            maruMonica=Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CAULDRON OBJECT
        Entity cauldron=new Obj_Cauldron(gp);
        cauldronImage=cauldron.image;

        // CREATE HUD OBJECT
        Entity heart=new Obj_Heart(gp);
        heart_full=heart.image;
        heart_half=heart.image2;
        heart_blank=heart.image3;

        // PUZZLE OBJECTS
        Entity star=new Obj_Star(gp);
        starImage=star.image;
    }
    public void draw(Graphics2D g2)
    {
        this.g2=g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState==gp.titleState)
        {
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState)
        {
            // Afisez mesajele pentru cand se termina nivelul
            if (gameFinished) {

                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

                g2.setFont(maruMonica);
                g2.setColor(Color.white);

                String text;
                int textLength;
                int x;
                int y;

                text = "CONGRATULATIONS! VELARIS IS YOURS AGAIN!";
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

                x = gp.screenWidth / 2 - textLength / 2; // aliniat in centru
                y = gp.screenHeight / 2 - (gp.tileSize);
                g2.drawString(text, x-4, y-4);

                gp.gameThread = null;

            } else {

                if(gp.currentMap==1 || gp.currentMap==0)
                {
                    drawPlayerLife();
                }

                if(gp.currentMap==2){

                    g2.setFont(arial_40);
                    g2.setColor(Color.white);
                    g2.setFont(g2.getFont().deriveFont(Font.ROMAN_BASELINE, 40F));


                    g2.drawImage(starImage, gp.tileSize / 2, gp.tileSize /2, gp.tileSize, gp.tileSize, null);
                    g2.drawString("x " + gp.player.matchCounter, 74, 65);
                }
            }
        }

        // PAUSE STATE
        if(gp.gameState== gp.pauseState)
        {
            drawPauseScreen();
        }

        // GAME OVER STATE
        if(gp.gameState==gp.gameOverState){
            drawGameOverScreen();
        }
    }

    public void drawPlayerLife(){

        int x=gp.tileSize/2;
        int y=gp.tileSize*11;
        int i=0;

        // DRAW MAX LIFE
        while(i<gp.player.maxLife/2)
        {
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+=gp.tileSize;
        }

        // RESET

        x=gp.tileSize/2;
        y=gp.tileSize*11;
        i=0;

        // DRAW CURRENT LIFE

        while(i<gp.player.life){

            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life){

                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
        }
    }
    public void drawTitleScreen() {

       // BACKGROUND IMAGE
            try{
                backgroundImage=ImageIO.read(getClass().getResourceAsStream("/interfata/interfata.png"));

            }catch(IOException e)
            {
                throw new RuntimeException(e);
            }
            g2.drawImage(backgroundImage,0,0,gp.screenWidth,gp.screenHeight,null);

            // FONT SETTINGS

            g2.setFont(maruMonica);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int x=gp.screenWidth-gp.tileSize*3;
            int y=gp.tileSize*8;

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,22F));

            String text="PLAY";
            y+=gp.tileSize;
            g2.drawString(text,x,y);

            if(commandNum==0)
            {
                g2.drawString(">>",x-gp.tileSize,y); // pot folosi drawImage in loc de draw string daca vreau sa folosesc o imagine
            }

            text="";
            y+=gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum==1)
            {
                g2.drawString(">>",x-gp.tileSize,y); // pot folosi drawImage in loc de draw string daca vreau sa folosesc o imagine
            }

            text="QUIT";
            y+=gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum==2)
            {
                g2.drawString(">>",x-gp.tileSize,y); // pot folosi drawImage in loc de draw string daca vreau sa folosesc o imagine
            }
    }

    public void drawPauseScreen()
    {
        gp.stop_music();

        try{
            pauseImage=ImageIO.read(getClass().getResourceAsStream("/interfata/pause.png"));

        }catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        g2.drawImage(pauseImage,0,0,gp.screenWidth,gp.screenHeight,null);

        // FONT SETTINGS

        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int y=gp.tileSize*6;

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        g2.setColor(Color.BLACK);

        String text= "RESUME";

        int x=getXforCenteredText(text);
        y+=gp.tileSize;
        g2.drawString(text,x,y);

        if(commandNum==0)
        {
            g2.drawString(">>",x-gp.tileSize,y); // pot folosi drawImage in loc de draw string daca vreau sa folosesc o imagine
        }

        text="MENU";
        x=getXforCenteredText(text);
        y+=gp.tileSize;
        g2.drawString(text,x,y);

        if(commandNum==2)
        {
            g2.drawString(">>",x-gp.tileSize,y);
        }

    }
    public void drawGameOverScreen()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x,y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));

        text="Game Over";

        //Shadow
        g2.setColor(Color.black);
        x=getXforCenteredText(text);
        y=gp.tileSize*4;
        g2.drawString(text,x,y);
        // Main

        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        // Retry

        g2.setFont(g2.getFont().deriveFont(50F));
        text="Retry";
        x=getXforCenteredText(text);
        y+=gp.tileSize*4;
        g2.drawString(text,x,y);

        if(commandNum==0)
        {
            g2.drawString(">", x-40,y);
        }

        // Back to the title screen

        text="Quit";
        x=getXforCenteredText(text);
        y+=55;
        g2.drawString(text,x,y);

        if(commandNum==1)
        {
            g2.drawString(">", x-40,y);
        }
    }
    public int getXforCenteredText(String text)
    {
        int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=gp.screenWidth/2-length/2;
        return x;
    }
}
