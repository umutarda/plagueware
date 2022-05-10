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

    private int gameMode;

    private boolean hasWon;

    public Game(User user, int gameMode) {
        this.setLayout(new BorderLayout());
        this.user = user;
        this.gameMode = gameMode;
        GameData.setUp(new Map(175, 95, 10, 1, "0".repeat(175*95)),
            new Virus(5, 15, 1000));
        setUpBuildings();
        GameData.generatePeople(200, 20, 50, 0, 20, 50);
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
            /*
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
            new Buildings.Hospital(60, 50);*/

            new Buildings.House(4, 2);
            new Buildings.House(4, 6);
            new Buildings.House(3, 11);
            new Buildings.House(8, 14);
            new Buildings.House(14, 14);
            new Buildings.House(17, 10);
            new Buildings.House(10, 7);
            new Buildings.House(10, 10);
            new Buildings.House(16, 5);
            new Buildings.House(11, 2);
            new Buildings.House(8, 4);
            new Buildings.House(19, 3);
            new Buildings.House(23, 5);
            new Buildings.House(24, 11);
            new Buildings.House(22, 15);
            new Buildings.House(17, 18);
            new Buildings.House(9, 19);
            new Buildings.House(3, 17);
            new Buildings.House(8, 22);
            new Buildings.House(15, 21);
            new Buildings.House(18, 21);
            new Buildings.House(22, 21);
            new Buildings.House(25, 20);
            new Buildings.House(27, 15);
            new Buildings.House(28, 10);
            new Buildings.House(27, 6);
            new Buildings.House(26, 2);
            new Buildings.Apartment(40, 3);
            new Buildings.Apartment(40, 9);
            new Buildings.Apartment(40, 15);
            new Buildings.Apartment(40, 19);
            new Buildings.Apartment(43, 23);
            new Buildings.Apartment(54, 24);
            new Buildings.Apartment(57, 24);
            new Buildings.Apartment(49, 23);
            new Buildings.Apartment(67, 24);
            new Buildings.Apartment(63, 24);
            new Buildings.Apartment(73, 24);
            new Buildings.Apartment(77, 25);
            new Buildings.Apartment(82, 25);
            new Buildings.Apartment(86, 24);
            new Buildings.Apartment(91, 23);
            new Buildings.Apartment(94, 20);
            new Buildings.Apartment(97, 16);
            new Buildings.Apartment(103, 11);
            new Buildings.Apartment(103, 7);
            new Buildings.Apartment(99, 3);
            new Buildings.Apartment(94, 2);
            new Buildings.Apartment(85, 2);
            new Buildings.Apartment(75, 2);
            new Buildings.Apartment(69, 2);
            new Buildings.Apartment(56, 2);
            new Buildings.Apartment(49, 3);
            new Buildings.Apartment(44, 4);
            new Buildings.House(50, 13);
            new Buildings.House(46, 9);
            new Buildings.House(47, 15);
            new Buildings.House(52, 18);
            new Buildings.House(57, 15);
            new Buildings.House(56, 9);
            new Buildings.House(52, 9);
            new Buildings.House(65, 15);
            new Buildings.House(63, 19);
            new Buildings.House(61, 19);
            new Buildings.House(63, 11);
            new Buildings.House(65, 8);
            new Buildings.House(71, 15);
            new Buildings.House(70, 19);
            new Buildings.House(74, 20);
            new Buildings.House(78, 18);
            new Buildings.House(78, 12);
            new Buildings.House(73, 8);
            new Buildings.House(71, 11);
            new Buildings.House(84, 14);
            new Buildings.House(85, 9);
            new Buildings.House(81, 8);
            new Buildings.House(81, 19);
            new Buildings.House(83, 21);
            new Buildings.House(88, 17);
            new Buildings.House(91, 14);
            new Buildings.House(91, 9);
            new Buildings.House(90, 6);
            new Buildings.House(96, 8);
            new Buildings.House(97, 10);
            new Buildings.Apartment(96, 25);
            new Buildings.Apartment(100, 25);
            new Buildings.Apartment(103, 24);
            new Buildings.Apartment(104, 19);
            new Buildings.Apartment(104, 16);
            new Buildings.House(100, 19);
            new Buildings.House(97, 21);
            new Buildings.Apartment(78, 14);
            new Buildings.Apartment(57, 19);
            new Buildings.Apartment(61, 2);
            new Buildings.Apartment(59, 9);
            new Buildings.Apartment(61, 14);
            new Buildings.Apartment(73, 12);
            new Buildings.Apartment(77, 7);
            new Buildings.Apartment(47, 18);
            new Buildings.House(44, 19);
            new Buildings.House(47, 21);
            new Buildings.Cafe(4, 47);
            new Buildings.Cafe(15, 47);
            new Buildings.Cafe(24, 46);
            new Buildings.Cafe(34, 47);
            new Buildings.Cafe(45, 47);
            new Buildings.Cafe(56, 47);
            new Buildings.Cafe(63, 48);
            new Buildings.Cafe(70, 48);
            new Buildings.Cafe(72, 51);
            new Buildings.Cafe(72, 58);
            new Buildings.Cafe(71, 63);
            new Buildings.Cafe(72, 67);
            new Buildings.Cafe(71, 74);
            new Buildings.Cafe(70, 82);
            new Buildings.Cafe(60, 84);
            new Buildings.Cafe(44, 86);
            new Buildings.Cafe(30, 86);
            new Buildings.Cafe(23, 86);
            new Buildings.Cafe(14, 85);
            new Buildings.Cafe(10, 83);
            new Buildings.Cafe(7, 77);
            new Buildings.Cafe(6, 70);
            new Buildings.Cafe(6, 63);
            new Buildings.Cafe(5, 55);
            new Buildings.Mall(17, 55);
            new Buildings.Mall(18, 66);
            new Buildings.Mall(20, 74);
            new Buildings.Mall(34, 76);
            new Buildings.Mall(48, 76);
            new Buildings.Mall(58, 74);
            new Buildings.Mall(62, 64);
            new Buildings.Mall(61, 55);
            new Buildings.Mall(27, 55);
            new Buildings.Mall(40, 55);
            new Buildings.Shop(32, 62);
            new Buildings.Shop(40, 63);
            new Buildings.Shop(50, 64);
            new Buildings.Cafe(29, 71);
            new Buildings.Cafe(38, 71);
            new Buildings.Cafe(49, 72);
            new Buildings.Mall(52, 56);
            new Buildings.Shop(88, 49);
            new Buildings.Shop(88, 56);
            new Buildings.Shop(89, 70);
            new Buildings.Shop(92, 81);
            new Buildings.Shop(103, 83);
            new Buildings.Shop(112, 83);
            new Buildings.Shop(121, 81);
            new Buildings.Shop(120, 74);
            new Buildings.Shop(119, 68);
            new Buildings.Shop(118, 60);
            new Buildings.Shop(117, 52);
            new Buildings.Shop(96, 51);
            new Buildings.Shop(102, 51);
            new Buildings.Shop(112, 52);
            new Buildings.Cafe(98, 60);
            new Buildings.Cafe(105, 61);
            new Buildings.Cafe(110, 62);
            new Buildings.Cafe(100, 67);
            new Buildings.Cafe(95, 65);
            new Buildings.Cafe(111, 72);
            new Buildings.Cafe(107, 67);
            new Buildings.Cafe(99, 73);
            new Buildings.Cafe(105, 73);
            new Buildings.Cafe(100, 79);
            new Buildings.Cafe(107, 79);
            new Buildings.Cafe(115, 79);
            new Buildings.Hospital(118, 19);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isOver() {
        return GameData.getPersonAmount() == 0 || GameData.getVirusAmount() == 0;
    }
    public boolean hasWon() {
        return hasWon;
        
    }

    private void endGame() {
        if(gameMode == SIMULATION) {
            hasWon = true;
        }
        else if(gameMode == END_BRINGER) {
            if(GameData.getPersonAmount() == 0) {
                hasWon = true;
            }
            hasWon = false;
        }
        // gameMode == SAVIOR
        if(GameData.getVirusAmount() == 0) {
            hasWon = true;
        }
        hasWon = false;
        

        if(!user.getNickname().equals("") && gameMode != Game.SIMULATION)
        {
            if(gameMode == Game.END_BRINGER)
                user.setGamemode("End-Bringer");
            if(gameMode == Game.SAVIOR)
                user.setGamemode("Saviour");



            user.setTime(GameData.time.getTotalMinutes());
            User.insertIntoDatabase(user);
            System.out.println("Inserted");
        }
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
