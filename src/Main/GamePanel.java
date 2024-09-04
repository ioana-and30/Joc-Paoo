package Main;

import AI.PathFinder;
import Entity.Entity;
import Entity.Player;
import Environment.environmentManager;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.* ;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize=16; // 16x16, default size for player,npc,tiles
    final int scale=3; // to scale tile size
    public final int tileSize=originalTileSize*scale; //48x4 tile
    public final int maxScreenCol=20;
    public final int maxScreenRow=12;
    public final int screenWidth=tileSize*maxScreenCol; // 960 pixels
    public final int screenHeight=tileSize*maxScreenRow; // 576 pixels


    // WORLD SETTINGS
    public final int maxWorldCol=40;
    public final int maxWorldRow=40;
    public final int maxMap=10; // we can create 10 different map
    public int currentMap=2; // indicates the current map number, where the character is
    // 2 - nivel 1
    // 0 - nivel 2
    // 1 - nivel 3


    // FPS
    int FPS=60;
    public TileManager tileM=new TileManager(this);
    public KeyHandler keyH=new KeyHandler(this);
    Sound music=new Sound();
    Sound se=new Sound();
    public CollisionChecker checker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);
    public UI ui=new UI(this);
    public EventHandler eHandler=new EventHandler(this);
    public PathFinder pFinder=new PathFinder(this);
    public environmentManager eManager=new environmentManager(this);
    Thread gameThread;


    // ENTITY AND OBJECT
    public Player player=new Player(this,keyH);
    public Entity obj[][]=new Entity[maxMap][20]; // 20 slots of object, we can display 10 objects at the same time
    public Entity monster[][]=new Entity[maxMap][20];
    public Entity projectile[][]=new Entity[maxMap][20];
//    public ArrayList<Entity> projectileList=new ArrayList<>();
    public ArrayList<Entity> entityList=new ArrayList<>();
    public ArrayList<Entity> particleList=new ArrayList<>();


    // GAME STATE
    public int gameState;
    public int titleState=0;
    public final int playState=1;
    public final int pauseState=2;
    public final int gameOverState=6;



    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.LIGHT_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() throws IOException {
        aSetter.setObject();
        aSetter.setMonster();
        eManager.setup();

        gameState=titleState;
    }
    public void startGameThread()
    {
        gameThread=new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        double drawInterval=1000000000/FPS; // draw the screen 16 times per second
        double nextDrawTime=System.nanoTime()+drawInterval;


        while(gameThread!=null)
        {
            long CurrentTime=System.nanoTime();

            //Update information such as character position
            update();

            //Draw the screen with the updated information
//            drawTempScreen(); // draw everything to the buffered image
//            drawScreen(); // draw the buffered image to the screen
            repaint();

            try {
                double remainingTime=nextDrawTime-System.nanoTime();
                remainingTime=remainingTime/1000000;

                if(remainingTime<0)
                {
                    remainingTime=0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update()
    {
        if(gameState==playState) {

            // PLAYER
            player.update();

            // MONSTER

            for(int i=0;i< monster.length;++i)
            {
                if(monster[currentMap][i]!=null)
                {
                    if(monster[currentMap][i].alive==true && monster[currentMap][i].dying==false){

                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive==false){

                        monster[currentMap][i]=null;
                    }
                }
            }

            for(int i=0;i<projectile[1].length;++i){
                if(projectile[currentMap][i]!=null){
                    if(projectile[currentMap][i].alive==true){
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive==false){
                        projectile[currentMap][i]=null;
                    }
                }
            }
        }

        for(int i=0;i<particleList.size();++i){
            if(particleList.get(i)!=null){
                if(particleList.get(i).alive==true){
                    particleList.get(i).update();
                }
                if(particleList.get(i).alive==false){
                    particleList.remove(i);
                }
            }
        }
        // PAUSE STATE
        if(gameState==pauseState)
        {
            //nothing now
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;

        // DEBUG

        long drawStart=0;
        if(keyH.checkDrawTime==true)
        {
            drawStart=System.nanoTime();
        }

        // TITLE SCREEN

        if(gameState==titleState)
        {
            ui.draw(g2);
        }

        // OTHERS
        else
        {
            // TILE
            tileM.draw(g2);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for(int i=0;i<obj[1].length;++i){
                if(obj[currentMap][i]!=null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            for(int i=0;i< monster[1].length;++i){
                if(monster[currentMap][i]!=null){
                    entityList.add(monster[currentMap][i]);
                }
            }

            for(int i=0;i<projectile[1].length;++i){
                if(projectile[currentMap][i]!=null){
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for(int i=0;i<particleList.size();++i){
                if(particleList.get(i)!=null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result=Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });

            // DRAW ENTITIES

            for(int i=0;i<entityList.size();++i)
            {
                entityList.get(i).draw(g2);
            }

            // EMPTY ENTITY LIST

            for(int i=0;i<entityList.size();++i)
            {
                entityList.remove(i);
            }

            // ENVIRONMENT
            eManager.draw(g2);

            // UI
            ui.draw(g2);
        }
        g2.dispose();
    }
    public void play_music(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stop_music()
    {
        music.stop();
    }
    public void sound_effect(int i)
    {
        se.setFile(i);
        se.play();
    }
    public void retry() throws IOException {
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setMonster();

    }
    public void restart() throws IOException {
        player.setDefaultValues();
        aSetter.setMonster();
    }




    // FUNCTIA DE INSERT
// INSERARE IN BAZA DE DATE
    public static void insertB(String nume_fisier, String nume_tabela, double a) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\BRAN IOANA ANDREEA\\Documents\\Anul II\\An 2 Sem II\\PAOO\\JOC\\Proiect/data.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "INSERT INTO " + nume_tabela + "(life) " + "VALUES (" + a + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Eroare la insert!!!");
            e.printStackTrace();
            // Optional rollback in case of error
            try {
                if (c != null) {
                    c.rollback();

                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    // FUNCTIA DE GET
//  EXTRAGERE CEA MAI RECENTA VALOARE DIN BAZA DE DATE
    public static int getB(String nume_fisier, String nume_tabela) {
        Connection c = null;
        Statement stmt = null;
        int value = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\BRAN IOANA ANDREEA\\Documents\\Anul II\\An 2 Sem II\\PAOO\\JOC\\Proiect/data.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM " + nume_tabela + ";");
            while (rs.next()) {
                value = rs.getInt("life");
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Eroare la get!!!");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return value;
    }
}
