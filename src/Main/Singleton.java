package Main;

import javax.swing.*;
import java.io.IOException;

public class Singleton {

    private static Singleton uniqueInstance = null;

    private Singleton() throws IOException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Prison Healer");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setUpGame();
        gamePanel.startGameThread();

    }

    // Metoda getInstance pentru sablonul Singleton
    public static Singleton getInstance() throws IOException {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
}
