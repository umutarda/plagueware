package entity;
import pathfinder.PathManager;
import pathfinder.PathfindNode;
import pathfinder.Node;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.UnaryOperator;

import main.Drawable;
import main.GameData;
import main.Main;
import main.Map;
import main.Updatable;
public class Person implements Updatable, Drawable{

    //variables
    public boolean isSick;
    public boolean mask = true;
    public boolean vaccinated;
    public boolean updated;
    public int age; // 0-100
    public int umut;
    public Point location;
    public Point currentNodePosition;
    public Point nextNodePosition;
    public double currentPercentage;

    public Building home;
    public Building currentBuilding;
    public int stress;//(discontent) 0-100 
    public int awareness; // 0-100
    public double penalty; //0-20

    public Object[] path;
    public int pathIndex;

    public Random random;

    private long leaveMinute; // the time at which the person will leave the building it is in
    
    public Person(boolean condition, boolean mask, boolean vaccinated, int age, Building home){
        this.isSick = condition;
        this.mask = mask;
        this.vaccinated = vaccinated;
        this.age = age;
        this.currentBuilding = null;
        this.pathIndex = -1;
        GameData.people.add(this);
        this.home = home;
        this.currentBuilding = home;
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
    public void setLeaveMinute(long minute) {
        leaveMinute = minute;
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
            if (pathIndex == -1 || currentPercentage == 1 )
            {
                if(pathIndex != -1) {
                    ((PathfindNode)path[pathIndex]).removePerson(this);
                }
                ((PathfindNode)path[++pathIndex]).addPerson(this);
                currentPercentage = 0;
                currentNodePosition = GameData.map.getPositionOfNode((Node)path[pathIndex]);
                
                if (pathIndex < path.length - 1)
                    nextNodePosition = GameData.map.getPositionOfNode((Node)path[pathIndex+1]);
                else 
                {
                    nextNodePosition = null;
                    location = null;
                    path = null;
                    pathIndex = -1;
                    if(currentBuilding != null) {
                        currentBuilding.enter(this);
                    }
                }
                
            }

            if (nextNodePosition != null) 
            {
                currentPercentage = Math.min(currentPercentage += 2 * GameData.updateManager.deltaTime(), 1);
                location.setLocation(currentNodePosition.x + (nextNodePosition.x - currentNodePosition.x) * currentPercentage,
                currentNodePosition.y + (nextNodePosition.y - currentNodePosition.y) * currentPercentage );
            }

        }
        else {
            if(GameData.time.getTotalMinutes() >= leaveMinute) {
                currentBuilding.exit(this);
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
    public void travelToBuilding(Building b) {

        // if (location == null)
        //     location = new Point (0,0);

       
        currentBuilding = b;
        GameData.pathManager.requestPath(this, GameData.map.getNodeAtPosition(location), b.getEnterNode());

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

    public void spread(Person p2)
    {
        if(isSick || p2.isSick)
        {
            this.isSick = true;
            p2.isSick = true;
        }
    }
    public void outsideContact()
    {
        Node[] contact = GameData.map.getNeighboursOf(GameData.map.getNodeAtPosition(location));
        for(Node node : contact)
        { 
            if( node instanceof PathfindNode)
            {
                PathfindNode pfNode = (PathfindNode) node;
                for (Person p : pfNode.getPersons()) {
                    if(!p.isSick)
                    {
                        double otherPenalty= p.getSpreadPenalty();

                        int dice = random.nextInt(21);

                        if (dice <= otherPenalty)
                        p.isSick = true;
                    }
                    
                }
            }
        }
    }

    public void insideContact()
    {
        
        for (Person person : currentBuilding.getPeople()) {
            if(!person.isSick)
            {
                double otherPenalty= person.getSpreadPenalty();

                int dice = random.nextInt(21);

                if (dice <= otherPenalty)
                   person.isSick = true;
            }
            
            
        }
    }


    @Override
    public void paint(Graphics g) {
        
        if (location == null)
            return;
        if(isSick) {
            g.setColor(Color.RED);
        }
        else {
            g.setColor(Color.GREEN);
        }
        g.fillOval(location.x, location.y, 10, 10);
    }

}
