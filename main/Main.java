package main;
import pathfinder.PathManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import entity.Building;
import entity.Buildings;
import entity.Person;
import entity.Time;


public class Main {
    
    public static void main(String[] args)  throws InterruptedException  {
        
        Map map = new Map(300, 100, 10, 1, "0".repeat(100*300));  
        GameData.setUp(map);
        GameData.drawManager.addDrawable(map);
        map.setWindowSize();

        JFrame frame = new JFrame();
        frame.setContentPane(GameData.drawManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);

        Building[] buildings = new Building[10];
        try {
            buildings[0] = new Buildings.Hospital(2, 10);
            buildings[1] = new Buildings.House(20, 10);
            buildings[2] = new Buildings.Cafe(50, 10);
            // buildings[3] = new Building(map, 100, 5, Building.APARTMENT);
            // buildings[4] = new Building(map, 180, 120, Building.CAFE);
            // buildings[5] = new Building(map, 20, 10, Building.CAFE);
            // buildings[6] = new Building(map, 20, 10, Building.CAFE);
            // buildings[7] = new Building(map, 20, 10, Building.CAFE);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Person aPerson = new Person(false, false, false, -1, new Point(0, 0));
        aPerson.travelToBuilding(buildings[0]);
        // pathManager.requestPath(aPerson, map.getNodeAtRowColumn(new Point (0,0)),  b.getEnterNode());

        Person bPerson = new Person(false, false, false, -1, map.getPositionOfNode(map.getNodeAtRowColumn(new Point(0, 0))));
        bPerson.travelToBuilding(buildings[1]);
       

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
