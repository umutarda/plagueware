package entity;
import java.awt.Point;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;

import main.*;
import pathfinder.*;
import java.awt.Color;
import java.awt.*;

public abstract class Building {
    
    
    
    private Point position;
    private ImpassibleNode[][] nodes;
    private Map map;
    private Node enterNode;
    private int buildingType;
    
    ArrayList<Person> persons;
    public Building(int x, int y) throws Exception {
        this.position = new Point(x, y);
        this.map = GameData.map;
        persons = new ArrayList<Person>();
        nodes = new ImpassibleNode[getNodeHeight()][getNodeWidth()];
        if(x + getNodeWidth() > map.getNodeWidth() || x < 0 ||
                y + getNodeHeight() > map.getNodeHeight() || y < 0 || getEnterNode() == null) {
            throw new Exception("Invalid Building Placement");
        }
        for(int i = 0; i < getNodeHeight(); i++) {
            for (int j = 0; j < getNodeWidth(); j++) {
                Node node = map.getNodeAtRowColumn(new Point(x + j, y + i));
                if(node instanceof ImpassibleNode) {
                    for (ImpassibleNode[] ins : nodes) {
                        for (ImpassibleNode in : ins) {
                            map.setNodePassible(in.getPosition());
                        }
                    }
                    throw new Exception("Invalid Building Placement");
                }
                map.setNodeImpassible(node.getPosition());
                nodes[i][j] = (ImpassibleNode) map.getNodeAtRowColumn(new Point(x + j, y + i));
                nodes[i][j].setColor(getColor());
            }
        }
        enterNode = getEnterNode();
        GameData.buildings.add(this);

    }
    public abstract int getNodeWidth(); 
    // {
    //     switch(buildingType) {
    //         case CAFE: return 3;
    //         case APARTMENT: return 2; 
    //         case HOSPITAL: return 8;
    //         case HOUSE: return 1;
    //     }
    //     return -1;
    // }
    public abstract int getNodeHeight();
    //  {
    //     switch(buildingType) {
    //         case CAFE: return 2;
    //         case APARTMENT: return 2; 
    //         case HOSPITAL: return 5;
    //         case HOUSE: return 1;
    //     }
    //     return -1;
    // }
    public Node getEnterNode() {
        Node pos = map.getNodeAtRowColumn(position);
        int x = pos.getPosition().x + getNodeWidth() / 2;
        int y = pos.getPosition().y - 1;
        Node node = map.getNodeAtRowColumn(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }
        y = pos.getPosition().y - getNodeHeight() + 1;
        node = map.getNodeAtRowColumn(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }
        y = pos.getPosition().y + getNodeHeight() / 2;
        x = pos.getPosition().x - 1;
        node = map.getNodeAtRowColumn(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }
        x = pos.getPosition().x + getNodeWidth() + 1;
        node = map.getNodeAtRowColumn(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }

        return null;
    }
    // public int getBuildingType() {
    //     return buildingType;
    // }
    public void enter(Person p) {
        persons.add(p);
        p.location = null;
    }
    public void exit(Person p) {
        persons.remove(p);
        p.location = map.getPositionOfNode(getEnterNode());
        //p.chooseBuilding();
    }
    public Person[] getPeople() {
        return (Person[])persons.toArray();
    }
    public abstract Color getColor();
    //  {
    //     switch(buildingType) {
    //         case CAFE: return Color.BLUE;
    //         case APARTMENT: return Color.GRAY;
    //         case HOSPITAL: return Color.RED;
    //         case HOUSE: return Color.ORANGE;
    //     }
    //     return null;
    // }
    public abstract int getBuildingType();
}