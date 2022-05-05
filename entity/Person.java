package entity;
import pathfinder.PathfindNode;
import pathfinder.Node;
import java.awt.*;
import java.util.Random;

import javax.swing.text.Position;

import main.Drawable;
import main.GameData;
import main.Updatable;
public class Person implements Updatable, Drawable{

    //variables
    public boolean isSick;
    public boolean mask = true;
    public boolean vaccinated;
    public boolean updated;
    public int age; // 0-100

    public Point location;
    public Point currentNodePosition;
    public Point nextNodePosition;
    public double currentPathIntervalPercentage;

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
        
        this.home = home;
        this.currentBuilding = home;
        this.currentPathIntervalPercentage = 1;

        random = new Random();

        GameData.people.add(this);
        GameData.updateManager.addUpdatable(this);
        GameData.drawManager.addDrawable(this);
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
    
    private boolean pathIntervalIsDone() 
    {
        return currentPathIntervalPercentage == 1;
    }
    
    private boolean pathTravelIsNotStarted() 
    {
        return pathIndex == -1;
    }

    private boolean pathTravelIsNotFinished() 
    {
        return pathIndex < path.length - 1;
    }

    @Override
    public void run() {

        if (path != null) 
        {
            if (pathIntervalIsDone())
            {
                if(pathTravelIsNotStarted()) {
                    location = new Point();
                }

                else 
                {
                    ((PathfindNode)path[pathIndex]).removePerson(this);
                }


                ((PathfindNode)path[++pathIndex]).addPerson(this);
                currentPathIntervalPercentage = 0;
                currentNodePosition = GameData.map.getPositionOfNode((Node)path[pathIndex]);
                
                if (pathTravelIsNotFinished())
                    nextNodePosition = GameData.map.getPositionOfNode((Node)path[pathIndex+1]);
                else 
                {
                    nextNodePosition = null;
                    location = null;
                    
                    currentBuilding.enter(this);
                    ((PathfindNode)path[pathIndex]).removePerson(this);
                    
                    path = null;
                    pathIndex = -1;
                    currentPathIntervalPercentage = 1;

                }
                
            }

            if (nextNodePosition != null) 
            {
                currentPathIntervalPercentage = Math.min(currentPathIntervalPercentage + 2 * GameData.updateManager.deltaTime(), 1);
                location.setLocation(currentNodePosition.x + (nextNodePosition.x - currentNodePosition.x) * currentPathIntervalPercentage,
                currentNodePosition.y + (nextNodePosition.y - currentNodePosition.y) * currentPathIntervalPercentage );
            }

            if (isSick && location != null)
                outsideContact();
        }
        else {
            if (currentBuilding != null) 
            {
                if (isSick)
                    insideContact();
                
            }
            if(GameData.time.getTotalMinutes() >= leaveMinute) {
                currentBuilding.exit(this);
            }
        }

        

       updated = true;
    }
    public void travelToBuilding(Building b) {

        // if (b == null)
        //     return;

        // Node startNode;
        // if (currentBuilding != null) 
        // {
        //     startNode = currentBuilding.getEnterNode();
        //     currentBuilding.exit(this);
        //     currentBuilding = null;
        // }
            
       
        // else
        //     startNode = GameData.map.getNodeAtPosition(location);

        currentBuilding = b;
        GameData.pathManager.requestPath(this, GameData.map.getNodeAtPosition(location), b.getEnterNode());
        // GameData.pathManager.requestPath(this, startNode, b.getEnterNode());

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
