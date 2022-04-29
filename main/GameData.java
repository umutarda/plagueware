package main;

import pathfinder.PathManager;

import java.util.ArrayList;
import java.util.Random;
import entity.Building;
import entity.Person;
import entity.Time;

public class GameData {
    public static Map map;
    public static PathManager pathManager;
    public static UpdateManager updateManager;
    public static DrawManager drawManager;
    static ArrayList<Building> entertainmentBuilding;
    static ArrayList<Person> people;
    static Building hospital;
    static Random rand = new Random();
    static Time time = new Time();

    
    //static Time time = new Time();


    static ArrayList<Person> generatePeople(int peopleCount, int illCount, double averMask, double averVacc, double averAge){//avermask 0-1  avervacc 0-1 average 0-100
        
        ArrayList<Person> ret = new ArrayList<Person>(peopleCount);

        for (int i = 0; i < peopleCount-illCount; i++) {
            boolean mask = (rand.nextDouble()-0.5+averMask)>0.5;
            boolean vaccinated = (rand.nextDouble()-0.5+averVacc)>0.5;
            int age =  Math.abs((int)((rand.nextGaussian()+1)*averAge));  
            ret.add(new Person(false, mask, vaccinated, age));

        }



        for (int i = 0; i < illCount; i++) {
            boolean mask = (rand.nextDouble()-0.5+averMask)>0.5;
            boolean vaccinated = (rand.nextDouble()-0.5+averMask)>0.5;
            int age = Math.abs((int)((rand.nextGaussian()+1)*averAge));
            ret.add(new Person(true, mask, vaccinated, age));

        }

        return ret;
        
    }

    static void resetAll() 
    {
        map = null;
        pathManager = null;
        updateManager = null;
        drawManager = null;
        entertainmentBuilding = null;
        people = null;
        hospital = null;
        
    }

    static void chooseBuilding(Person p){
        int hour = time.hour;
        if(hour>24 || hour<8)
            pathManager.requestPath(p,  map.getNodeAtPosition(p.location) , p.house.getEnterNode());

        else if(hour>8 && hour<16)
            pathManager.requestPath(p, map.getNodeAtPosition(p.location) , entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size())).getEnterNode() );
        
        else if(hour>16 && hour<24)
            pathManager.requestPath(p, map.getNodeAtPosition(p.location) , entertainmentBuilding.get(rand.nextInt(entertainmentBuilding.size())).getEnterNode() );


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
