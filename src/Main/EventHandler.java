package Main;

import java.awt.*;

import static Main.GamePanel.insertB;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY; // un ev se intampla din nou doar daca player-ul se misca la un tile distanta

    boolean canTouchEvent=true;

    public  EventHandler(GamePanel gp)
    {
        this.gp=gp;
        eventRect=new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map=0;
        int col=0;
        int row=0;

        while(map<gp.maxMap && col<gp.maxWorldCol && row<gp.maxWorldRow)
        {
            eventRect[map][col][row]=new EventRect();
            // trigger point at the middle of the tile
            eventRect[map][col][row].x=23;
            eventRect[map][col][row].y=23;
            // the event handler is triggered when the player solid area hits the edge of the tile
            eventRect[map][col][row].width=2;
            eventRect[map][col][row].height=2;
            eventRect[map][col][row].eventRectDefX=eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefY=eventRect[map][col][row].y;

            col++;

            if(col==gp.maxWorldCol)
            {
                col=0;
                row++;

                if(row==gp.maxWorldRow)
                {
                    row=0;
                    map++;
                }
            }
        }

    }

    public void checkEvent()
    {
        // Check if the player character is more than 1 tile away from the last event

        int xDistance=Math.abs(gp.player.worldX-previousEventX); // pure distance between the objects
        int yDistance=Math.abs(gp.player.worldY-previousEventY);
        int distance=Math.max(xDistance, yDistance);

        if(distance > gp.tileSize)
        {
            canTouchEvent=true;
        }

        if(canTouchEvent==true)
        {
            if(hit(0,38,3,"any")==true){
                insertB("data", "Data_table",gp.player.life);
                teleport(1,15,28);}
            else if (hit(2,3,6,"any")==true){

                if(gp.player.matchCounter==8){
                    insertB("data", "Data_table",gp.player.life);
                    teleport(0,24,38);
                }
            }
            else if(hit(1,15,24,"up")==true){

                insertB("data", "Data_table",gp.player.life);
                teleport(1,22,18);}

            else if(hit(1,22,15,"up")==true){

                insertB("data", "Data_table",gp.player.life);
                teleport(1,21,13);}
        }
    }

    public boolean hit(int map,int col, int row, String reqDirection)
    {
        boolean hit=false;

        if(map== gp.currentMap)
        {
            gp.player.solidArea.x=gp.player.worldX+gp.player.solidArea.x;
            gp.player.solidArea.y=gp.player.worldY+gp.player.solidArea.y;
            eventRect[map][col][row].x=col*gp.tileSize+eventRect[map][col][row].x;
            eventRect[map][col][row].y=row*gp.tileSize+eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone==false) // event-ul se intampla numai cand eventDone e fals
            {
                if(gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any"))
                {
                    hit=true;

                    previousEventX=gp.player.worldX;
                    previousEventY=gp.player.worldY;
                }
            }

            gp.player.solidArea.x=gp.player.solidAreaDefaultX;
            gp.player.solidArea.y=gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x=eventRect[map][col][row].eventRectDefX;
            eventRect[map][col][row].y=eventRect[map][col][row].eventRectDefY;

        }

        return hit;
    }

    public void teleport(int map, int col, int row)
    {
        gp.currentMap=map;
        gp.player.worldX=gp.tileSize*col;
        gp.player.worldY=gp.tileSize*row;
        previousEventX=gp.player.worldX;
        previousEventY=gp.player.worldY;
        canTouchEvent=false;
    }

//    public void damagePit(int col, int row,int gameState) // nu mi dau seama daca functioneaza sau nu, nu cred ???!!
//    {
//        gp.gameState=gameState;
//        gp.player.speed+=1; // trebuie sa gasesc o solutie cum sa fac sa i se reduca viteza fara sa mearga inapoi
//
////        eventRect[col][row].eventDone=true;
//        canTouchEvent=false;
//    }

//    public void healingEv(int col, int row,int gameState)
//    {
//        if(gp.keyH.healingKeyPressed==true)
//        {
//            gp.gameState=gameState;
//            gp.player.life=gp.player.maxLife;
//        }
//
//        gp.keyH.healingKeyPressed=false;
//    }



}
