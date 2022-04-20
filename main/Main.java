package main;
import pathfinder.PathManager;
import java.awt.*;
import javax.swing.JFrame;


public class Main {
    
    public static GameData gameData;
    public static void main(String[] args)  throws InterruptedException  {
        
        JFrame frame = new JFrame();
        Map map = new Map(50, 50, 4, 3, "0".repeat(50*5) + "1".repeat(49) + "0".repeat(50*44 + 1));                                   

        frame.setContentPane(map);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);

        PathManager pathManager = new PathManager(map);
        UpdateManager updateManager = new UpdateManager(map);
      
        for (int i = 0; i < 100; i++) {
            
            pathManager.requestPath(null, new Point(0,0), new Point(49,49));
        }
        updateManager.addUpdatable(pathManager);        
        
        while (true) {
            updateManager.update();
        }

    }

}
