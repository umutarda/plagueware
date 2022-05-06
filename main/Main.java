package main;
import pathfinder.PathManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

import entity.Building;
import entity.Buildings;
import entity.Person;
import entity.Time;



public class Main {
    
    public static void main(String[] args)  throws InterruptedException  {
        
        System.out.println(" Enter the username: ");
        Scanner in = new Scanner(System.in);
        String a = in.nextLine();
        User user = new User(a);

        Map map = new Map(300, 100, 10, 1, "0".repeat(100*300));  
        GameData.setUp(map);
        GameData.drawManager.addDrawable(map);
        map.setWindowSize();

        JFrame frame = new JFrame();
        frame.setTitle("PLAGUEWARE");

        JPanel gamePanel = new JPanel(new BorderLayout());
        frame.add(gamePanel);

        gamePanel.add(GameData.drawManager, BorderLayout.CENTER);
        gamePanel.add(new InfoPanel(), BorderLayout.NORTH);
        // frame.setContentPane(GameData.drawManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);

        // frame.add(new InfoPanel());

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
            new Buildings.House(30, 60);
            new Buildings.House(30, 40);
            new Buildings.Cafe(80, 20);
            new Buildings.Cafe(80, 30);
            new Buildings.Cafe(90, 10);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Person aPerson = new Person(false, false, false, -1, new Point(0, 0));
        // aPerson.house = buildings[1];
        // updateManager.addUpdatable(aPerson);
        // drawManager.addDrawable(aPerson);
        // aPerson.travelToBuilding(buildings[0]);
        // // pathManager.requestPath(aPerson, map.getNodeAtRowColumn(new Point (0,0)),  b.getEnterNode());

        // Person bPerson = new Person(false, false, false, -1, new Point(0, 0));
        // bPerson.house = buildings[1];
        // updateManager.addUpdatable(bPerson);
        // drawManager.addDrawable(bPerson);
        // bPerson.travelToBuilding(buildings[1]);
        GameData.generatePeople(20, 5, 50, 0, 20);


       

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // System.out.println("a: " + aPerson.location + ", b: " + bPerson.location);
                // System.out.println("a: " + aPerson.currentNodePosition + ", b: " + bPerson.currentNodePosition);
                // System.out.println("Day " + GameData.time.day + ", " + GameData.time.hour + ":" + GameData.time.minute);
            }
            
        }, 0, 1000);
        // frame.addMouseListener(new MouseInputListener() {

        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         System.out.println(e.getX() + ", " + e.getY());
        //         map.getNodeAtPosition(new Point(e.getX(), e.getY())).setColor(Color.BLUE);
        //         System.out.println(map.getNodeAtPosition(new Point(e.getX(), e.getY())).getPosition());
        //     }

        //     @Override
        //     public void mousePressed(MouseEvent e) {
        //         // TODO Auto-generated method stub
                
        //     }

        //     @Override
        //     public void mouseReleased(MouseEvent e) {
        //         // TODO Auto-generated method stub
                
        //     }

        //     @Override
        //     public void mouseEntered(MouseEvent e) {
        //         // TODO Auto-generated method stub
                
        //     }

        //     @Override
        //     public void mouseExited(MouseEvent e) {
        //         // TODO Auto-generated method stub
                
        //     }

        //     @Override
        //     public void mouseDragged(MouseEvent e) {
        //         // TODO Auto-generated method stub
                
        //     }

        //     @Override
        //     public void mouseMoved(MouseEvent e) {
        //         // TODO Auto-generated method stub
                
        //     }
            
        // });

        while (true) {
            GameData.updateManager.update();
            
        }

    }

}
