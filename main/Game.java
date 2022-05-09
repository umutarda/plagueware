package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.*;
import pathfinder.*;
import java.awt.*;

public class Game extends JPanel {
    private User user;


    public static final int SIMULATION = 0;
    public static final int END_BRINGER = 1;
    public static final int SAVIOR = 2;

    public Game(User user, int gameMode) {
        this.setLayout(new BorderLayout());
        this.user = user;
        GameData.setUp(new Map(200, 70, 10, 1, "0".repeat(70*200)),
            new Virus(50, 15, 10));
        setUpBuildings();
        GameData.generatePeople(50, 5, 50, 0, 20, 50);
        add(GameData.drawManager, BorderLayout.CENTER);
        add(new InfoPanel(), BorderLayout.NORTH);
        setBackground(Color.BLACK);
        setVisible(true);
        if(gameMode == END_BRINGER) {
            this.add(new EndBringer(100).skillPanel, BorderLayout.EAST);
        }
        else if(gameMode == SAVIOR) {
            this.add(new Savior(100).skillPanel, BorderLayout.EAST);
        }


    }
    public void start() {
        
        while(!isOver()) {
            GameData.updateManager.update();
        }
        endGame();
    }

    private void setUpBuildings() {
        try {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    new Buildings.House(1 + 2 * i, 1 + 2 * j + i % 2);
                }
            }
            for (int i = 0; i < 5; i++) {
                new Buildings.Apartment(1, 50 + i * 3);
            }
            for (int i = 0; i < 10; i++) {
                new Buildings.House(90, 10 + 4 * i);
            }
            
            new Buildings.Cafe(30, 30);
            new Buildings.Mall(35, 30);
            new Buildings.Shop(50, 30);
            new Buildings.Cafe(55, 30);
            new Buildings.Cafe(30, 20);
            new Buildings.Mall(35, 17);
            new Buildings.Shop(50, 18);
            new Buildings.Cafe(55, 20);
            new Buildings.Hospital(60, 50);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected boolean isOver() {
        return GameData.getPersonAmount() == 0 || GameData.getVirusAmount() == 0;
    }

    protected void endGame() {
        GameData.resetAll();

    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("PLAGUEWARE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
        Game game = new Game(new User("kerem"), SAVIOR);
        frame.add(game);
        frame.pack();
        // game.add(new EndBringer(100).skillPanel, BorderLayout.EAST);
        game.start();


    }

    
}
