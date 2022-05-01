package entity;
import pathfinder.PathfindNode;
import pathfinder.Node;
import java.awt.*;
import java.util.Random;


import main.Drawable;
import main.GameData;
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
    public double currentPathIntervalPercentage;

    public Building house;
    public int stress;//(discontent) 0-100 
    public int awareness; // 0-100
    public double penalty; //0-20
    private Building currentBuilding;

    public Object[] path;
    public int pathIndex;

    public Random random;
    
    public Person(boolean condition, boolean mask, boolean vaccinated, int age, Point position){
        this.condition = condition;
        this.mask = mask;
        this.vaccinated = vaccinated;
        this.age = age;
        this.currentBuilding = null;
        this.pathIndex = -1;
        this.currentPathIntervalPercentage = 1;
        this.location = position;

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
                currentPathIntervalPercentage = Math.min(currentPathIntervalPercentage + 20 * GameData.updateManager.deltaTime(), 1);
                location.setLocation(currentNodePosition.x + (nextNodePosition.x - currentNodePosition.x) * currentPathIntervalPercentage,
                currentNodePosition.y + (nextNodePosition.y - currentNodePosition.y) * currentPathIntervalPercentage );
            }

            if (condition)
                outsideContact();
        }

        else 
        {
            if (currentBuilding != null) 
            {
                if (condition)
                    insideContact();
                
            }

            //TODO fix the problem of calling random building func every other frame while random building is being null
            travelToBuilding(GameData.randomBuildingForPerson(this));
            
        }

       updated = true;
    }
    public void travelToBuilding(Building b) {

        if (b == null)
            return;

        Node startNode;
        if (currentBuilding != null) 
        {
            startNode = currentBuilding.getEnterNode();
            currentBuilding.exit(this);
            currentBuilding = null;
        }
            
       
        else
            startNode = GameData.map.getNodeAtPosition(location);

        currentBuilding = b;
        GameData.pathManager.requestPath(this, startNode, b.getEnterNode());

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
        double otherPenalty= p2.getSpreadPenalty();

        int spreadChance = random.nextInt(21);

        if (spreadChance <= otherPenalty)
            p2.condition = true;   
               
    }
    public void outsideContact()
    {
        Node[] contact = GameData.map.getNeighboursOf(GameData.map.getNodeAtPosition(location));
        for(Node x : contact)
        { 
            if( x instanceof PathfindNode)
            {
                PathfindNode A = (PathfindNode) x ;
                for (Person person : A.getPersons()) {
                    
                    if(!person.condition)
                        spread(person);
                    
                }
            }
        }
    }

    public void insideContact()
    {
     
        for (Person person : currentBuilding.getPeople()) {
            if(!person.condition)
                spread(person);
            
        }
    }


    @Override
    public void paint(Graphics g) {
        
        if (location == null)
            return;
        if(condition) {
            g.setColor(Color.RED);
        }
        else {
            g.setColor(Color.GREEN);
        }
        g.fillOval(location.x, location.y, 10, 10);
    }

}
