package main;

import pathfinder.PathManager;
import java.util.ArrayList;
import java.util.Random;
import entity.*;

public class GameData {
    public static Map map;
    public static PathManager pathManager;
    public static UpdateManager updateManager;
    public static DrawManager drawManager;
    public static ArrayList<Building> buildings;
    public static ArrayList<Person> people;
    public static Buildings.Hospital hospital;
    static Random rand = new Random();
    public static Time time;
    public static Virus virus;

    public static final int PERSON_SPEED = 20;
    public static final int TIME_FACTOR = 8;
    
    //static Time time = new Time();
    public static ArrayList<Building> getEntertainmentBuildings() {
        ArrayList<Building> entertainment = new ArrayList<Building>();
        for (Building building : buildings) {
            if(building.getBuildingType() == Buildings.ENTERTAINMENT) {
                entertainment.add(building);
            }
        }
        return entertainment;
    }
    public static ArrayList<Building> getHomeBuildings() {
        ArrayList<Building> homes = new ArrayList<Building>();
        for (Building building : buildings) {
            if(building.getBuildingType() == Buildings.HOME) {
                homes.add(building);
            }
        }
        return homes;
    }


    public static void generatePeople(int peopleCount, int illCount, double averMask, double averVacc, double averAge, double averAwareness){//avermask 0-1  avervacc 0-1 average 0-100
        ArrayList<Building> homes = getHomeBuildings();
    
        

        for (int i = 0; i < peopleCount; i++) {
            boolean condition = false;
            if(i < illCount) {
                condition = true;
            }
            boolean mask = (rand.nextDouble() - 0.5 + averMask) > 0.5;
            boolean vaccinated = (rand.nextDouble() - 0.5 + averVacc) > 0.5;
            int age =  Math.abs((int)((rand.nextGaussian() + 1) * averAge));
            int awareness =  Math.abs((int)((rand.nextGaussian() + 1) * averAge));  

            //adds to the people list in constructor
            new Person(condition, mask, vaccinated, age, homes.get(rand.nextInt(homes.size())), awareness);

        }
    }
    public static int getPersonAmount() {
        return people.size() - getDeadAmount();
    }
    public static int getDeadAmount() {
        int deadAmount = 0;
        for (Person p : people) {
            if(p.isDead) {
                deadAmount++;
            }
        }
        return deadAmount;
    }
    public static int getVirusAmount() {
        int virusAmount = 0;
        for (Person p : people) {
            if(p.isSick) {
                virusAmount++;
            }
        }
        return virusAmount;
    }
    public static int getAsymptomaticAmount() {
        int asymptomaticAmount = 0;
        for (Person p : people) {
            if(p.isAsymptomatic) {
                asymptomaticAmount++;
            }
        }
        return asymptomaticAmount;
    }

    public static void resetAll() 
    {
        map = null;
        pathManager = null;
        updateManager = null;
        drawManager = null;
        buildings = null;
        people = null;
        hospital = null;
        time = null;
    }

    public static void chooseBuilding(Person p){
        int hour = time.hour;
        
        if (rand.nextInt(20) == 0 || (p.isSick && !p.isAsymptomatic && p.awareness > rand.nextGaussian(50, 20))) 
        {
            p.travelToBuilding(hospital);
        
        }
        else 
        {
            ArrayList<Building> entertainmentBuilding = getEntertainmentBuildings();
            if(hour<8){
                
                p.travelToBuilding(p.home);
            }

            else
                p.travelToBuilding(entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size())));
        }
        
        
    }
    public static double getAvgAge() {
        int ageSum = 0;
        for (Person person : people) {
            if(!person.isDead){
                ageSum += person.age;
            } 
        }
        return (double) ageSum / getPersonAmount();
    }
    public static double getAvgAwareness() {
        int awarenessSum = 0;
        for (Person person : people) {
            if(!person.isDead){
                awarenessSum += person.awareness;
            } 
        }
        return (double) awarenessSum / getPersonAmount();
    }
    public static double getVaccinatedRatio() {
        int vaccSum = 0;
        for (Person person : people) {
            if(!person.isDead && person.vaccinated){
                vaccSum += 1;
            } 
        }
        return (double) vaccSum / getPersonAmount();
    }

    public static Building randomBuildingForPerson(Person p){
        int hour = time.hour;
        ArrayList<Building> entertainmentBuilding = getEntertainmentBuildings();

        boolean stay = rand.nextBoolean();
        
        if (stay)
            return null;

        if(hour<=8)
            return p.home;

        else if(hour>8 && hour<=16)
            return entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size()));
        
        else if(hour>16)
            return entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size()));

        return null;


    }
    public static void setUp(Map newMap, Virus newVirus) {
        
        map = newMap;
        virus = newVirus;
        drawManager = new DrawManager();
        updateManager = new UpdateManager();
        pathManager = new PathManager(map);
        buildings = new ArrayList<Building>();
        people = new ArrayList<Person>();
        time = new Time();
        drawManager.addDrawable(map);
        map.setWindowSize();
        updateManager.addUpdatable(pathManager);
    }

    // public static void main(String[] args) {
    //     ArrayList<Person> x =  generatePeople(300, 0, 0.99, 0.99, 30);

    //     int mask = 0;
    //     int vac = 0;
    //     int age = 0;
    //     int illCount = 0;
    //     int count = 0;

    //     for (Person person : x) {
    //         if(person.mask)
    //             mask++;
    //         if(person.vaccinated)
    //             vac++;
    //         if(person.condition)
    //             illCount++;

    //         count++;
    //         age += person.age;
    // }
    
    // System.out.println(1.0*mask/x.size());
    // System.out.println(1.0*vac/x.size());
    // System.out.println(1.0*age/x.size()); 
    // System.out.println(illCount);
    // System.out.println(count);

    // for (Person person : x) {
    //     System.out.println("Mask: " +person.mask+", Vacc: "+person.vaccinated+", Age: " +person.age);
    // }

    
    // }
}
