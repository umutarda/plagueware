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

            new Buildings.House(6, 3);
            new Buildings.House(4, 5);
            new Buildings.House(5, 10);
            new Buildings.House(5, 7);
            new Buildings.House(11, 6);
            new Buildings.House(11, 10);
            new Buildings.House(8, 11);
            new Buildings.House(11, 15);
            new Buildings.House(13, 14);
            new Buildings.House(15, 11);
            new Buildings.House(14, 4);
            new Buildings.House(13, 7);
            new Buildings.House(19, 6);
            new Buildings.House(17, 16);
            new Buildings.House(7, 21);
            new Buildings.House(4, 17);
            new Buildings.House(12, 21);
            new Buildings.House(17, 18);
            new Buildings.House(24, 12);
            new Buildings.House(22, 17);
            new Buildings.House(21, 23);
            new Buildings.House(6, 24);
            new Buildings.House(15, 24);
            new Buildings.House(8, 27);
            new Buildings.House(15, 27);
            new Buildings.House(11, 24);
            new Buildings.House(8, 18);
            new Buildings.House(3, 13);
            new Buildings.House(1, 9);
            new Buildings.House(20, 9);
            new Buildings.House(20, 13);
            new Buildings.House(24, 5);
            new Buildings.House(21, 3);
            new Buildings.House(17, 2);
            new Buildings.House(13, 2);
            new Buildings.House(10, 2);
            new Buildings.House(7, 14);
            new Buildings.House(27, 21);
            new Buildings.House(26, 25);
            new Buildings.House(22, 26);
            new Buildings.House(20, 26);
            new Buildings.House(25, 27);
            new Buildings.House(18, 22);
            new Buildings.House(24, 18);
            new Buildings.House(29, 18);
            new Buildings.House(29, 14);
            new Buildings.House(26, 15);
            new Buildings.House(29, 10);
            new Buildings.House(25, 9);
            new Buildings.Apartment(45, 4);
            new Buildings.Apartment(45, 9);
            new Buildings.Apartment(46, 17);
            new Buildings.Apartment(45, 21);
            new Buildings.Apartment(46, 25);
            new Buildings.Apartment(71, 24);
            new Buildings.Apartment(56, 25);
            new Buildings.Apartment(54, 22);
            new Buildings.Apartment(70, 19);
            new Buildings.Apartment(65, 20);
            new Buildings.Apartment(68, 12);
            new Buildings.Apartment(71, 10);
            new Buildings.Apartment(71, 6);
            new Buildings.Apartment(66, 4);
            new Buildings.Apartment(57, 3);
            new Buildings.Apartment(51, 5);
            new Buildings.Apartment(51, 11);
            new Buildings.House(56, 15);
            new Buildings.House(52, 16);
            new Buildings.House(52, 19);
            new Buildings.House(58, 19);
            new Buildings.House(61, 22);
            new Buildings.House(64, 13);
            new Buildings.House(62, 8);
            new Buildings.House(58, 8);
            new Buildings.House(60, 10);
            new Buildings.House(67, 17);
            new Buildings.House(62, 17);
            new Buildings.House(55, 10);
            new Buildings.House(69, 8);
            new Buildings.House(65, 25);
            new Buildings.Apartment(62, 26);
            new Buildings.House(69, 24);
            new Buildings.House(68, 27);
            new Buildings.House(1, 24);
            new Buildings.House(2, 20);
            new Buildings.House(2, 28);
            new Buildings.House(4, 26);
            new Buildings.Cafe(9, 47);
            new Buildings.Cafe(18, 47);
            new Buildings.Cafe(31, 48);
            new Buildings.Cafe(39, 48);
            new Buildings.Cafe(54, 49);
            new Buildings.Cafe(55, 56);
            new Buildings.Cafe(9, 53);
            new Buildings.Cafe(3, 54);
            new Buildings.Cafe(4, 62);
            new Buildings.Cafe(9, 70);
            new Buildings.Cafe(20, 70);
            new Buildings.Cafe(37, 70);
            new Buildings.Cafe(54, 70);
            new Buildings.Cafe(58, 65);
            new Buildings.Mall(20, 57);
            new Buildings.Mall(36, 59);
            new Buildings.Mall(49, 64);
            new Buildings.Mall(12, 63);
            new Buildings.Mall(32, 54);
            new Buildings.Cafe(29, 71);
            new Buildings.Cafe(47, 72);
            new Buildings.Cafe(47, 48);
            new Buildings.Mall(48, 53);
            new Buildings.Cafe(26, 48);
            new Buildings.Mall(27, 64);
            new Buildings.Hospital(150, 76);
            new Buildings.Cafe(66, 49);
            new Buildings.Cafe(62, 49);
            new Buildings.Cafe(77, 49);
            new Buildings.Cafe(73, 53);
            new Buildings.Cafe(96, 52);
            new Buildings.Cafe(91, 47);
            new Buildings.Cafe(97, 58);
            new Buildings.Cafe(96, 66);
            new Buildings.Cafe(90, 71);
            new Buildings.Cafe(67, 71);
            new Buildings.Cafe(70, 60);
            new Buildings.Cafe(82, 65);
            new Buildings.Mall(84, 57);
            new Buildings.Mall(76, 69);
            new Buildings.House(5, 81);
            new Buildings.House(10, 80);
            new Buildings.House(19, 80);
            new Buildings.House(27, 82);
            new Buildings.House(39, 80);
            new Buildings.House(54, 81);
            new Buildings.House(66, 81);
            new Buildings.House(78, 81);
            new Buildings.House(93, 79);
            new Buildings.House(99, 76);
            new Buildings.House(103, 71);
            new Buildings.House(104, 64);
            new Buildings.House(104, 58);
            new Buildings.House(105, 50);
            new Buildings.House(105, 47);
            new Buildings.House(85, 78);
            new Buildings.House(42, 87);
            new Buildings.House(47, 77);
            new Buildings.House(14, 87);
            new Buildings.House(5, 88);
            new Buildings.House(28, 87);
            new Buildings.House(20, 83);
            new Buildings.House(7, 75);
            new Buildings.Shop(97, 9);
            new Buildings.Shop(110, 11);
            new Buildings.Shop(99, 19);
            new Buildings.Shop(135, 14);
            new Buildings.Shop(129, 5);
            new Buildings.Shop(126, 21);
            new Buildings.Cafe(118, 20);
            new Buildings.Cafe(114, 19);
            new Buildings.Cafe(105, 14);
            new Buildings.Cafe(106, 7);
            new Buildings.Cafe(124, 13);
            new Buildings.Cafe(119, 11);
            new Buildings.Cafe(120, 6);
            new Buildings.Mall(145, 22);
            new Buildings.Shop(144, 9);
            new Buildings.Shop(141, 5);
            new Buildings.Shop(137, 24);
            new Buildings.Shop(155, 14);
            new Buildings.Shop(153, 5);
            new Buildings.Shop(157, 24);
            new Buildings.Cafe(144, 16);
            new Buildings.Cafe(150, 18);
            new Buildings.Cafe(132, 18);
            new Buildings.Cafe(133, 24);
            new Buildings.Cafe(126, 27);
            new Buildings.Cafe(115, 26);
            new Buildings.Cafe(109, 24);
            new Buildings.Cafe(106, 23);
            new Buildings.Cafe(100, 26);
            new Buildings.Cafe(94, 22);
            new Buildings.Cafe(95, 17);
            new Buildings.Apartment(141, 43);
            new Buildings.Apartment(142, 49);
            new Buildings.Apartment(134, 56);
            new Buildings.Apartment(134, 47);
            new Buildings.Apartment(145, 63);
            new Buildings.Apartment(155, 59);
            new Buildings.Apartment(154, 47);
            new Buildings.House(154, 64);
            new Buildings.House(150, 69);
            new Buildings.House(132, 69);
            new Buildings.House(138, 63);
            new Buildings.House(144, 60);
            new Buildings.House(147, 55);
            new Buildings.House(129, 52);
            new Buildings.House(133, 41);
            new Buildings.House(145, 39);
            new Buildings.House(151, 41);
            new Buildings.House(147, 46);
            new Buildings.House(153, 52);
            new Buildings.House(160, 54);
            new Buildings.House(163, 50);
            new Buildings.House(163, 44);
            new Buildings.House(157, 42);
            new Buildings.Apartment(163, 63);
            new Buildings.Apartment(164, 57);
            new Buildings.House(161, 61);
            new Buildings.House(160, 67);
            new Buildings.House(164, 68);
            new Buildings.House(169, 63);
            new Buildings.House(131, 58);
            new Buildings.House(132, 63);
            new Buildings.House(103, 43);
            new Buildings.House(97, 44);
            new Buildings.House(101, 46);
            new Buildings.House(139, 67);
            new Buildings.House(145, 68);
            new Buildings.Cafe(85, 48);

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
        else {
            if(GameData.getVirusAmount() == 0) {
                hasWon = true;
            }
            hasWon = false;
        }
        

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
