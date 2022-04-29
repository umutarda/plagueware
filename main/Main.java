package main;
import pathfinder.PathManager;
import java.awt.*;
import javax.swing.JFrame;

import entity.Building;
import entity.Person;


public class Main {
    
    public static void main(String[] args)  throws InterruptedException  {
        
        DrawManager drawManager = new DrawManager();
        GameData.drawManager = drawManager;

        Map map = new Map(300, 100, 10, 1, "0".repeat(100*300));  
        GameData.map = map;
        drawManager.addDrawable(map);

        PathManager pathManager = new PathManager(map);
        GameData.pathManager = pathManager;

        UpdateManager updateManager = new UpdateManager(drawManager);
        GameData.updateManager = updateManager;
        updateManager.addUpdatable(pathManager); 

        JFrame frame = new JFrame();                                        
        frame.setContentPane(drawManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);

        Building[] buildings = new Building[10];
        try {
            buildings[0] = new Building(map, 1, 1, Building.HOSPITAL);
            buildings[1] = new Building(map, 20, 10, Building.CAFE);
            buildings[2] = new Building(map, 50, 10, Building.HOUSE);
            buildings[3] = new Building(map, 100, 5, Building.APARTMENT);
            // buildings[4] = new Building(map, 180, 120, Building.CAFE);
            // buildings[5] = new Building(map, 20, 10, Building.CAFE);
            // buildings[6] = new Building(map, 20, 10, Building.CAFE);
            // buildings[7] = new Building(map, 20, 10, Building.CAFE);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Person aPerson = new Person(false, false, false, -1, new Point(0, 0));
        updateManager.addUpdatable(aPerson);
        drawManager.addDrawable(aPerson);
        aPerson.travelToBuilding(buildings[0]);
        // pathManager.requestPath(aPerson, map.getNodeAtRowColumn(new Point (0,0)),  b.getEnterNode());

        Person bPerson = new Person(false, false, false, -1, new Point(10,10));
        updateManager.addUpdatable(bPerson);
        drawManager.addDrawable(bPerson);
        pathManager.requestPath(bPerson,  map.getNodeAtRowColumn(new Point (5,0)),  map.getNodeAtRowColumn(new Point (30,35)));
       


        while (true) {
            updateManager.update();
        }

    }

}
