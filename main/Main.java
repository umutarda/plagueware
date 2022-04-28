package main;
import pathfinder.PathManager;
import java.awt.*;
import javax.swing.JFrame;

import entity.Person;


public class Main {
    
    public static GameData gameData;
    public static void main(String[] args)  throws InterruptedException  {
        
        
        DrawManager drawManager = new DrawManager();
        gameData.drawManager = drawManager;

        Map map = new Map(50, 50, 4, 3, "0".repeat(50*5) + "1".repeat(49) + "0".repeat(50*44 + 1));  
        gameData.map = map;
        drawManager.addDrawable(map);

        PathManager pathManager = new PathManager(map);
        gameData.pathManager = pathManager;

        UpdateManager updateManager = new UpdateManager(drawManager);
        gameData.updateManager = updateManager;

        JFrame frame = new JFrame();
                                        
        frame.setContentPane(drawManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);

       /* for (int i = 0; i < 100; i++) {
            
            pathManager.requestPath(null, new Point(0,0), new Point(49,49));
        }*/
        updateManager.addUpdatable(pathManager);        
        
        Person aPerson = new Person(false, false, false, -1);
        updateManager.addUpdatable(aPerson);
        drawManager.addDrawable(aPerson);
        pathManager.requestPath(aPerson, new Point (0,0), new Point (40,49));

        Person bPerson = new Person(false, false, false, -1);
        updateManager.addUpdatable(bPerson);
        drawManager.addDrawable(bPerson);
        pathManager.requestPath(bPerson, new Point (5,0), new Point (30,35));

        while (true) {
            updateManager.update();
        }

    }

}
