package Main;

import monster.Mon_Amarantha;
import monster.Mon_Dragon;
import monster.Mon_Skeleton;
import object.*;

import java.io.IOException;

public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp)
    {
        this.gp=gp;
    }

    public void setObject()
    {
        // SET OBJECTS IN MAP 0

        int mapNum=0;

        // mushroom 1 start left
        gp.obj[mapNum][1]=new Obj_Mushroom(gp);
        gp.obj[mapNum][1].worldX=23*gp.tileSize;
        gp.obj[mapNum][1].worldY=38*gp.tileSize;
        gp.obj[mapNum][1].collision=true;

        // mushroom 2 start left
        gp.obj[mapNum][2]=new Obj_Mushroom(gp);
        gp.obj[mapNum][2].worldX=23*gp.tileSize;
        gp.obj[mapNum][2].worldY=37*gp.tileSize;
        gp.obj[mapNum][2].collision=true;

        // mushroom 1 start right
        gp.obj[mapNum][3]=new Obj_Mushroom(gp);
        gp.obj[mapNum][3].worldX=26*gp.tileSize;
        gp.obj[mapNum][3].worldY=38*gp.tileSize;
        gp.obj[mapNum][3].collision=true;

        // mushroom 2 start right
        gp.obj[mapNum][4]=new Obj_Mushroom(gp);
        gp.obj[mapNum][4].worldX=26*gp.tileSize;
        gp.obj[mapNum][4].worldY=37*gp.tileSize;
        gp.obj[mapNum][4].collision=true;


        // WINGS
        gp.obj[mapNum][5]=new Obj_Wings(gp);
        gp.obj[mapNum][5].worldX=9*gp.tileSize;
        gp.obj[mapNum][5].worldY=22*gp.tileSize;
        gp.obj[mapNum][5].collision=true;

        gp.obj[mapNum][6]=new Obj_Wings(gp);
        gp.obj[mapNum][6].worldX=28*gp.tileSize;
        gp.obj[mapNum][6].worldY=16*gp.tileSize;
        gp.obj[mapNum][6].collision=true;

        gp.obj[mapNum][7]=new Obj_Wings(gp);
        gp.obj[mapNum][7].worldX=34*gp.tileSize;
        gp.obj[mapNum][7].worldY=14*gp.tileSize;
        gp.obj[mapNum][7].collision=true;

        gp.obj[mapNum][8]=new Obj_Wings(gp);
        gp.obj[mapNum][8].worldX=20*gp.tileSize;
        gp.obj[mapNum][8].worldY=9*gp.tileSize;
        gp.obj[mapNum][8].collision=true;

        gp.obj[mapNum][9]=new Obj_Wings(gp);
        gp.obj[mapNum][9].worldX=9*gp.tileSize;
        gp.obj[mapNum][9].worldY=2*gp.tileSize;
        gp.obj[mapNum][9].collision=true;

        // SET OBJECTS IN MAP 1

        mapNum=1;

        gp.obj[mapNum][0]=new Obj_Crown(gp);
        gp.obj[mapNum][0].worldX=19*gp.tileSize;
        gp.obj[mapNum][0].worldY=10*gp.tileSize;
        gp.obj[mapNum][0].collision=true;

        // SET OBJECTS IN MAP 2

        mapNum=2;

        gp.obj[mapNum][0]=new Obj_Cazan(gp);
        gp.obj[mapNum][0].worldX=30*gp.tileSize;
        gp.obj[mapNum][0].worldY=34*gp.tileSize;
        gp.obj[mapNum][0].collision=true;

        gp.obj[mapNum][1]=new Obj_Cazan(gp);
        gp.obj[mapNum][1].worldX=22*gp.tileSize;
        gp.obj[mapNum][1].worldY=12*gp.tileSize;
        gp.obj[mapNum][1].collision=true;

        gp.obj[mapNum][2]=new Obj_Zana(gp);
        gp.obj[mapNum][2].worldX=24*gp.tileSize;
        gp.obj[mapNum][2].worldY=25*gp.tileSize;
        gp.obj[mapNum][2].collision=true;

        gp.obj[mapNum][3]=new Obj_Zana(gp);
        gp.obj[mapNum][3].worldX=6*gp.tileSize;
        gp.obj[mapNum][3].worldY=33*gp.tileSize;
        gp.obj[mapNum][3].collision=true;

        gp.obj[mapNum][4]=new Obj_Dragon(gp);
        gp.obj[mapNum][4].worldX=21*gp.tileSize;
        gp.obj[mapNum][4].worldY=4*gp.tileSize;
        gp.obj[mapNum][4].collision=true;

        gp.obj[mapNum][5]=new Obj_Dragon(gp);
        gp.obj[mapNum][5].worldX=9*gp.tileSize;
        gp.obj[mapNum][5].worldY=20*gp.tileSize;
        gp.obj[mapNum][5].collision=true;

        gp.obj[mapNum][6]=new Obj_Blazon(gp);
        gp.obj[mapNum][6].worldX=16*gp.tileSize;
        gp.obj[mapNum][6].worldY=32*gp.tileSize;
        gp.obj[mapNum][6].collision=true;

        gp.obj[mapNum][7]=new Obj_Blazon(gp);
        gp.obj[mapNum][7].worldX=14*gp.tileSize;
        gp.obj[mapNum][7].worldY=25*gp.tileSize;
        gp.obj[mapNum][7].collision=true;
    }

    public void setMonster() throws IOException {

        int mapNum=0;

        gp.monster[mapNum][0]=new Mon_Skeleton(gp);
        gp.monster[mapNum][0].worldX=gp.tileSize*11;
        gp.monster[mapNum][0].worldY=gp.tileSize*3;

        gp.monster[mapNum][1]=new Mon_Skeleton(gp);
        gp.monster[mapNum][1].worldX=gp.tileSize*33;
        gp.monster[mapNum][1].worldY=gp.tileSize*4;

        gp.monster[mapNum][2]=new Mon_Skeleton(gp);
        gp.monster[mapNum][2].worldX=gp.tileSize*16;
        gp.monster[mapNum][2].worldY=gp.tileSize*6;

        gp.monster[mapNum][3]=new Mon_Skeleton(gp);
        gp.monster[mapNum][3].worldX=gp.tileSize;
        gp.monster[mapNum][3].worldY=gp.tileSize*9;

//        gp.monster[mapNum][4]=new Mon_Skeleton(gp);
//        gp.monster[mapNum][4].worldX=gp.tileSize*17;
//        gp.monster[mapNum][4].worldY=gp.tileSize*16;

        gp.monster[mapNum][4]=new Mon_Skeleton(gp);
        gp.monster[mapNum][4].worldX=gp.tileSize*9;
        gp.monster[mapNum][4].worldY=gp.tileSize*16;

//        gp.monster[mapNum][6]=new Mon_Skeleton(gp);
//        gp.monster[mapNum][6].worldX=gp.tileSize*4;
//        gp.monster[mapNum][6].worldY=gp.tileSize*22;

        gp.monster[mapNum][5]=new Mon_Skeleton(gp);
        gp.monster[mapNum][5].worldX=gp.tileSize*12;
        gp.monster[mapNum][5].worldY=gp.tileSize*24;

//        gp.monster[mapNum][6]=new Mon_Skeleton(gp);
//        gp.monster[mapNum][6].worldX=gp.tileSize*5;
//        gp.monster[mapNum][6].worldY=gp.tileSize*26;

//        gp.monster[mapNum][9]=new Mon_Skeleton(gp);
//        gp.monster[mapNum][9].worldX=gp.tileSize*27;
//        gp.monster[mapNum][9].worldY=gp.tileSize*34;

        mapNum=1;

        gp.monster[mapNum][0]= new Mon_Dragon(gp);
        gp.monster[mapNum][0].worldX=gp.tileSize*14;
        gp.monster[mapNum][0].worldY=gp.tileSize*25;

        gp.monster[mapNum][1]= new Mon_Dragon(gp);
        gp.monster[mapNum][1].worldX=gp.tileSize*16;
        gp.monster[mapNum][1].worldY=gp.tileSize*26;

        gp.monster[mapNum][1]= new Mon_Dragon(gp);
        gp.monster[mapNum][1].worldX=gp.tileSize*20;
        gp.monster[mapNum][1].worldY=gp.tileSize*17;

//        gp.monster[mapNum][3]= new Mon_Dragon(gp);
//        gp.monster[mapNum][3].worldX=gp.tileSize*20;
//        gp.monster[mapNum][3].worldY=gp.tileSize*16;

        gp.monster[mapNum][2]= new Mon_Amarantha(gp);
        gp.monster[mapNum][2].worldX=gp.tileSize*19;
        gp.monster[mapNum][2].worldY=gp.tileSize*13;
    }
}
