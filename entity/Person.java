package entity;
import pathfinder.PathManager;
import pathfinder.PathfindNode;
import pathfinder.Node;
import java.awt.*;
import java.util.Random;

import main.Main;
import main.Map;
import main.Updatable;
public class Person implements Updatable {

    //variables
    boolean condition;
    boolean mask = true;
    boolean vaccinated;
    int age; // 0-100
    Point coordinate;
    Building house;
    int stress;//(discontent) 0-100 
    int awareness; // 0-100
    double penalty; //0-20

    PathfindNode[] path;
    int pathIndex;

    Random random;
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
        // TODO Auto-generated method stub
      
        Map map = Main.gameData.map;
        
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
        }
        
       // pathManager.requestPath(this, startPosition, targetPosition);
    }

    @Override
    public boolean hasFullyUpdated() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void reset() {
        penalty = -1;
        
    }

}
