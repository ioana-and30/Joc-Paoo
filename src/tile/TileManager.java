package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    public boolean drawPath=true;

    public TileManager(GamePanel gp) throws IOException {
        this.gp=gp;
        tile=new Tile[100];
        mapTileNum=new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map_world.txt",0);
        loadMap("/maps/nivel2.txt",1);
        loadMap("/maps/nivel0.txt",2);
    }

    public void getTileImage() throws IOException {
        try {
            setup(0, "wall", true);
            setup(1, "water", true);
            setup(2, "grass", false);
            setup(3, "door", false);
            setup(4, "zid", true);
            setup(5, "podea", false);
            setup(6, "tron", true);
            setup(7, "bones", false);
            setup(8, "c1", false);
            setup(9, "c2", false);
            setup(10, "c3", false);
            setup(11, "c4", false);
            setup(12, "c5", false);
            setup(13, "c6", false);
            setup(14, "c7", false);
            setup(15, "c8", false);
            setup(16, "c9", false);
            setup(17, "magic", false);
            setup(18, "copac", true);
            setup(19, "grass_niv1", false);
            setup(20, "flower", true);
            setup(21, "zid_1", true);
            setup(22, "zid_2", true);
            setup(23, "zid_3", true);
            setup(24, "zid_4", true);
            setup(25, "zid_5", true);
            setup(26, "zid_6", true);
            setup(27, "zid_7", true);
            setup(28, "zid_8", true);
            setup(29, "zid_9", false);
            setup(30, "zid_10", false);
            setup(31, "zid_11", false);
            setup(32, "zid_12", false);
            setup(33, "tron_1", true);
            setup(34, "tron_2", true);
            setup(35, "tron_3", true);
            setup(36, "tron_4", true);
            setup(37, "tron_5", true);
            setup(38, "tron_6", true);
            setup(39, "tron_7", true);
            setup(40, "tron_8", true);
            setup(41, "tron_9", true);
            setup(42, "tron_10", false);
            setup(43, "tron_11", false);
            setup(44, "tron_12", false);
            setup(45, "cell_4", true);
            setup(46, "cell_2", true);
            setup(47, "cell_3", true);
            setup(48, "cell_1", true);
            setup(49, "covor", false);
            setup(50, "sfesnic", true);

        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    public void setup(int index,String imageName, boolean collision) throws IOException {
        UtilityTool uTool=new UtilityTool();
        try{
            tile[index]=new Tile();
            tile[index].image=ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
            tile[index].image=uTool.scaleImage(tile[index].image, gp.tileSize,gp.tileSize);
            tile[index].collision=collision;

        }catch(IOException e){

            throw new RuntimeException(e);
        }

    }
    public void loadMap(String filePath, int map)
    {
        try{
            InputStream is =getClass().getResourceAsStream(filePath);
            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            int col=0;
            int row=0;

            while(col<gp.maxWorldCol && row<gp.maxWorldRow)
            {
                String line=br.readLine();

                while(col<gp.maxWorldCol)
                {
                    String numbers[]=line.split(" ");
                    int num=Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row]=num;
                    col++;
                }
                if(col==gp.maxWorldCol)
                {
                    col=0;
                    row++;
                }
            }

            br.close();

        }catch(Exception e)
        {

        }
    }
    public void draw(Graphics2D g2)
    {
        int worldCol=0;
        int worldRow=0;

        while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow)
        {
            int tileNum=mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX=worldCol*gp.tileSize;
            int worldY=worldRow*gp.tileSize;
            int screenX=worldX-gp.player.worldX+gp.player.screenX;
            int screenY=worldY-gp.player.worldY+gp.player.screenY;

            if(worldX+gp.tileSize > gp.player.worldX-gp.player.screenX &&
               worldX-gp.tileSize < gp.player.worldX+gp.player.screenX &&
               worldY+gp.tileSize > gp.player.worldY-gp.player.screenY &&
               worldY-gp.tileSize < gp.player.worldY+gp.player.screenY)
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }

            worldCol++;

            if(worldCol==gp.maxWorldCol)
            {
                worldCol=0;
                worldRow++;
            }
        }

        if(drawPath==true){
            g2.setColor(new Color(0,0,0,10));
            for(int i=0;i<gp.pFinder.pathList.size();++i){

                int worldX=gp.pFinder.pathList.get(i).col*gp.tileSize;
                int worldY=gp.pFinder.pathList.get(i).row*gp.tileSize;
                int screenX=worldX-gp.player.worldX+gp.player.screenX;
                int screenY=worldY-gp.player.worldY+gp.player.screenY;

                g2.fillRect(screenX,screenY,gp.tileSize,gp.tileSize);
            }
        }
    }
}
