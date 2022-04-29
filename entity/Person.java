package entity;
import pathfinder.PathManager;
import pathfinder.PathfindNode;
import pathfinder.Node;
import java.awt.*;
import java.util.Random;
import java.util.function.UnaryOperator;

import main.Drawable;
import main.Main;
import main.Map;
import main.Updatable;
public class Person implements Updatable, Drawable{

    //variables
    public boolean condition;
    public boolean mask = true;
    public boolean vaccinated;
    public boolean updated;
    public int age; // 0-100
    
    public Point location;
    public Point currentNodePosition;
    public Point nextNodePosition;
    public double currentPercentage;

    public Building house;
    public int stress;//(discontent) 0-100 
    public int awareness; // 0-100
    public double penalty; //0-20

    public Object[] path;
    public int pathIndex;

    public Random random;
    public Person(boolean condition, boolean mask, boolean vaccinated, int age){
        this.condition = condition;
        this.mask = mask;
        this.vaccinated = vaccinated;
        this.age = age;
    }
    public double getSpreadPenalty(){
         
        if(penalty == -1)
            calculateSpreadPenalty();

        return penalty;
    }

    public void setPath (Object[] path) 
    {
        this.path = path;
    }
    private void calculateSpreadPenalty(){
        penalty = 0;
        if(!vaccinated)
            penalty += 5;
        if(!mask)
            penalty += 5;
        penalty += age * 0.03 + stress * 0.03 + (100-awareness) * 0.04;
   }

    @Override
    public void run() {

        if (path != null) 
        {
            if (location == null || currentPercentage == 1 )
            {
                currentPercentage = 0;
                currentNodePosition = Main.gameData.map.getPositionOfNode((Node)path[++pathIndex]);
                
                if (pathIndex < path.length - 1)
                    nextNodePosition = Main.gameData.map.getPositionOfNode((Node)path[pathIndex+1]);
                else 
                    nextNodePosition = null;
                
            }

            if (location == null)
                location = new Point (-1,-1);
            
            if (nextNodePosition != null) 
            {
                currentPercentage = Math.min(currentPercentage += 20 * Main.gameData.updateManager.deltaTime(), 1);
                location.setLocation(currentNodePosition.x + (nextNodePosition.x - currentNodePosition.x) * currentPercentage,
                currentNodePosition.y + (nextNodePosition.y - currentNodePosition.y) * currentPercentage );
            }

        }

       
      
        /*Map map = Main.gameData.map;
        
        if(condition) 
        {
            Node[] neighbourNodes =  map.getNeighboursOf(map.getNodeAtPosition(coordinate));
            for (int i = 0; i < neighbourNodes.length; i++) {
                    
                if (neighbourNodes[i] == null)
                    continue;

                Person p = map.getPersonAtNode(neighbourNodes[i]);
                double otherPenalty= p.getSpreadPenalty();

                int die = random.nextInt(21);

                if (die <= otherPenalty)
                    p.condition = true;
            
            }
        }*/
        
       // pathManager.requestPath(this, startPosition, targetPosition);


       updated = true;
    }

    @Override
    public boolean hasFullyUpdated() {
        // TODO Auto-generated method stub
        return updated;
    }
    @Override
    public void reset() {
        penalty = -1;
        updated = false;
    }

    @Override
    public void paint(Graphics g) {
        
        if (location == null)
            return;
        g.setColor(Color.RED);
        g.fillOval(location.x, location.y, 5, 5);
    }

}
