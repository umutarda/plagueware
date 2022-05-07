package entity;
import pathfinder.PathfindNode;
import pathfinder.Node;
import pathfinder.PathManager;

import java.awt.*;
import java.util.Random;

import javax.swing.plaf.synth.SynthToolTipUI;
import javax.swing.text.Position;

import main.Drawable;
import main.Game;
import main.GameData;
import main.Updatable;
import main.UpdateManager;
public class Person implements Updatable, Drawable{

    //variables
    public boolean isSick;
    public boolean isDead;
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
    
    private double secondTimer;


    public Person(boolean condition, boolean mask, boolean vaccinated, int age, Building home, int awareness){
        this.isDead = false;
        this.isSick = condition;
        this.mask = mask;
        this.vaccinated = vaccinated;
        this.age = age;
        this.awareness = awareness;
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

    

    private void updateTravelValues() 
    {
        if (pathIndex != -1 && path.length == 1 && ((Node)path[0]).getPosition().getX() == 91 && ((Node)path[0]).getPosition().getY() == 9)
                System.out.println();

        if(!pathTravelIsNotStarted()) 
            ((PathfindNode)path[pathIndex]).removePerson(this);
    
        pathIndex++;

        ((PathfindNode)path[pathIndex]).addPerson(this);        
        currentNodePosition = GameData.map.getPositionOfNode((Node)path[pathIndex]);
        
        if (pathTravelIsNotFinished()) 
        {
            currentPathIntervalPercentage = 0;
            nextNodePosition = GameData.map.getPositionOfNode((Node)path[pathIndex+1]);
        }

        else
            finishPathTravel();
                             
    }  


    private void finishPathTravel() 
    {
        currentNodePosition = null;
        nextNodePosition = null;
        location = null;

        currentBuilding.enter(this);
        ((PathfindNode)path[pathIndex]).removePerson(this);
        
        path = null;
        pathIndex = -1;
        currentPathIntervalPercentage = 1;

        if (currentBuilding == GameData.hospital) 
        {
            // double destiny = random.nextDouble();
            // isSick = destiny < .6f;
            // isDead = destiny > .9f;
            if(random.nextDouble() < getDeathChance()) {
                isDead = true;
            }
            else if(random.nextDouble() < getRecoveryChance()) {
                isSick = false;
            }

            if(isDead) {
                isSick = false;
            }
        }                

    }
    private double getDeathChance() {
        double chance = GameData.virus.getMortality() / 100.0;
        chance += (age - 40) / 500.0;
        if(vaccinated) {
            chance -= 0.2;
        }

        return chance;
    }
    private double getRecoveryChance() {
        return 1 - getDeathChance() - 0.9;
    }

    private void contact() 
    {
        if (path != null)
            if (location != null)
                outsideContact();

        else 
        {
            if (currentBuilding != null) 
                insideContact();
            
        }
    }

    private void spreadToOther(Person p)
    {
        double otherPenalty= p.getSpreadPenalty();
        int dice = random.nextInt(21);

        if (dice <= otherPenalty) {
            p.isSick = true;
        }
    }
    private void outsideContact()
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
                        spreadToOther(p);
                    }
                    
                }
            }
        }
    }

    private void insideContact()
    {
        
        for (Person person : currentBuilding.getPeople()) {
            if(!person.isSick)
            {
                double otherPenalty= person.getSpreadPenalty();

                int dice = random.nextInt(21);

                if (dice <= otherPenalty)
                    spreadToOther(person);
            }
            
            
        }
    }

    //private 

    @Override
    public void run() {

        if (isDead)
            return;

        if (path != null) 
        {
            if (pathIntervalIsDone())
                updateTravelValues();

            if (path != null) 
            {
                currentPathIntervalPercentage = Math.min(currentPathIntervalPercentage + GameData.PERSON_SPEED * GameData.updateManager.deltaTime(), 1);
                location.setLocation(currentNodePosition.x + (nextNodePosition.x - currentNodePosition.x) * currentPathIntervalPercentage,
                currentNodePosition.y + (nextNodePosition.y - currentNodePosition.y) * currentPathIntervalPercentage );
            }
        }

        else // path == null
        {
            if(GameData.time.getTotalMinutes() >= leaveMinute) 
            {
                leaveMinute = Long.MAX_VALUE;
                currentBuilding.exit(this);
            }
                
            
        }

        
        if (secondTimer >= 1) 
        {
            if (isSick)
                contact();

            secondTimer = 0;
        }

        secondTimer += GameData.updateManager.deltaTime();
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
        
        if (isDead)
            return;

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
