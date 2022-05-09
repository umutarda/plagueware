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
            new Virus(50, 50, 10));
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
            new Buildings.Hospital(2, 10);
            new Buildings.House(20, 10);
            new Buildings.House(30, 10);
            new Buildings.House(30, 30);
            new Buildings.House(40, 10);
            new Buildings.House(50, 10);
            new Buildings.House(60, 30);
            new Buildings.House(70, 10);
            new Buildings.House(80, 10);
            new Buildings.House(90, 30);
            new Buildings.House(100, 10);
            for (int i = 0; i < 10; i++) {
                new Buildings.House(0, 6 * i);
            }
            new Buildings.House(30, 60);
            new Buildings.House(30, 40);
            new Buildings.Cafe(80, 20);
            new Buildings.Cafe(80, 30);
            new Buildings.Cafe(90, 10);

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
        Game game = new Game(new User("kerem"), END_BRINGER);
        frame.add(game);
        frame.pack();
        // game.add(new EndBringer(100).skillPanel, BorderLayout.EAST);
        game.start();


    }

    
}
