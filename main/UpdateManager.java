package main;
import java.util.ArrayList;

public class UpdateManager{
    
    public final long UPDATE_TIME = 33333333;
    private ArrayList<Updatable> updatables;
    private ArrayList<Updatable> requestedUpdatables;
    private DrawManager drawManager;

    private double deltaTime;

    public UpdateManager(DrawManager drawManager) 
    {
        this.drawManager = drawManager;
        updatables = new ArrayList<Updatable>();
        requestedUpdatables = new ArrayList<Updatable>();
    }

    public double deltaTime() 
    {
        return deltaTime;
    }

    public void addUpdatable (Updatable updatable) 
    {
        requestedUpdatables.add(updatable);
    }

    public void update() 
    {
        long startTime = System.nanoTime();
        int index = 0;

        while (System.nanoTime() < startTime + UPDATE_TIME) 
        {
            if (updatables.size() == 0)
                continue;

            Updatable updatable = updatables.get(index);

            if (!updatable.hasFullyUpdated())
                updatable.run();
            
            index++; 
            if (index == updatables.size())
                index = 0;

        }
        
        for (Updatable updatable : updatables) {
           updatable.reset(); 
        }

        Main.gameData.drawManager.repaint();

        updatables.addAll(requestedUpdatables);
        requestedUpdatables.clear();

        deltaTime = (1.0 * (System.nanoTime() - startTime)) / Math.pow(10, 9);
    }

}
