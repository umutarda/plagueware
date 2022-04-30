package main;

import pathfinder.PathManager;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import entity.Building;
import entity.Buildings;
import entity.Person;
import entity.Time;

public class GameData {
    public static Map map;
    public static PathManager pathManager;
    public static UpdateManager updateManager;
    public static DrawManager drawManager;
    public static ArrayList<Building> buildings;
    public static ArrayList<Person> people;
    public static Buildings.Hospital hospital;
    static Random rand = new Random();
    static Time time;

    
    //static Time time = new Time();
    static ArrayList<Building> getEntertainmentBuildings() {
        ArrayList<Building> entertainment = new ArrayList<Building>();
        for (Building building : buildings) {
            if(building.getBuildingType() == Buildings.ENTERTAINMENT) {
                entertainment.add(building);
            }
        }
        return entertainment;
    }
    static ArrayList<Building> getHomeBuildings() {
        ArrayList<Building> homes = new ArrayList<Building>();
        for (Building building : buildings) {
            if(building.getBuildingType() == Buildings.HOME) {
                homes.add(building);
            }
        }
        return homes;
    }


    static ArrayList<Person> generatePeople(int peopleCount, int illCount, double averMask, double averVacc, double averAge){//avermask 0-1  avervacc 0-1 average 0-100
        
        ArrayList<Person> ret = new ArrayList<Person>(peopleCount);

        for (int i = 0; i < peopleCount-illCount; i++) {
            boolean mask = (rand.nextDouble()-0.5+averMask)>0.5;
            boolean vaccinated = (rand.nextDouble()-0.5+averVacc)>0.5;
            int age =  Math.abs((int)((rand.nextGaussian()+1)*averAge));  
            ret.add(new Person(false, mask, vaccinated, age, new Point(0,0))); //fix position

        }



        for (int i = 0; i < illCount; i++) {
            boolean mask = (rand.nextDouble()-0.5+averMask)>0.5;
            boolean vaccinated = (rand.nextDouble()-0.5+averMask)>0.5;
            int age = Math.abs((int)((rand.nextGaussian()+1)*averAge));
            ret.add(new Person(true, mask, vaccinated, age, new Point(0,0))); //fix position

        }

        return ret;
        
    }

    static void resetAll() 
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

    static void chooseBuilding(Person p){
        int hour = time.hour;
        ArrayList<Building> entertainmentBuilding = getEntertainmentBuildings();
        if(hour>24 || hour<8)
            pathManager.requestPath(p,  map.getNodeAtPosition(p.location) , p.house.getEnterNode());

        else if(hour>8 && hour<16)
            pathManager.requestPath(p, map.getNodeAtPosition(p.location) , entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size())).getEnterNode() );
        
        else if(hour>16 && hour<24)
            pathManager.requestPath(p, map.getNodeAtPosition(p.location) , entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size())).getEnterNode() );


    }
    public static void setUp(Map newMap) {
        map = newMap;
        pathManager = new PathManager(map);
        drawManager = new DrawManager();
        updateManager = new UpdateManager(drawManager);
        buildings = new ArrayList<Building>();
        people = new ArrayList<Person>();
        time = new Time();
    }

    public static void main(String[] args) {
        ArrayList<Person> x =  generatePeople(300, 0, 0.99, 0.99, 30);

        int mask = 0;
        int vac = 0;
        int age = 0;
        int illCount = 0;
        int count = 0;

        for (Person person : x) {
            if(person.mask)
                mask++;
            if(person.vaccinated)
                vac++;
            if(person.condition)
                illCount++;

            count++;
            age += person.age;
    }
    
    System.out.println(1.0*mask/x.size());
    System.out.println(1.0*vac/x.size());
    System.out.println(1.0*age/x.size()); 
    System.out.println(illCount);
    System.out.println(count);

    for (Person person : x) {
        System.out.println("Mask: " +person.mask+", Vacc: "+person.vaccinated+", Age: " +person.age);
    }

    
    }
}
