import java.util.ArrayList;

public class UpdateManager{
    
    public final long UPDATE_TIME = 33333333;
    private ArrayList<Updatable> updatables;
    private ArrayList<Updatable> requestedUpdatables;
    private Map map;

    public UpdateManager(Map map) 
    {
        this.map = map;
        updatables = new ArrayList<Updatable>();
        requestedUpdatables = new ArrayList<Updatable>();
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
        
        map.repaint();

        updatables.addAll(requestedUpdatables);
        requestedUpdatables.clear();
    }

}
