package entity;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import main.*;
import pathfinder.*;
import java.awt.Color;
import java.awt.*;

public class Building {
    public final static int CAFE = 0;
    public final static int APARTMENT = 1;
    public final static int HOSPITAL = 2;
    public final static int HOUSE = 3;
    
    private ImpassibleNode[][] nodes;
    private Map map;
    private Node enterNode;
    private int buildingType;
    ArrayList<Person> persons;
    public Building(Map map, int x, int y, int buildingType) throws Exception {
        this.buildingType = buildingType;
        nodes = new ImpassibleNode[getNodeHeight()][getNodeWidth()];
        if(x + getNodeWidth() > map.getNodeWidth() || x < 0 ||
                y + getNodeHeight() > map.getNodeHeight() || y < 0) {
            throw new Exception("Invalid Building Placement");
        }
        for(int i = 0; i < getNodeHeight(); i++) {
            for (int j = 0; j < getNodeWidth(); j++) {
                Node node = map.getNodeAtPosition(new Point(x + j, y + i));
                if(node instanceof ImpassibleNode) {
                    for (ImpassibleNode[] ins : nodes) {
                        for (ImpassibleNode in : ins) {
                            map.setNodePassible(in.getPosition());
                        }
                    }
                    throw new Exception("Invalid Building Placement");
                }
                map.setNodeImpassible(node.getPosition());
                nodes[i][j] = (ImpassibleNode) map.getNodeAtPosition(new Point(x + j, y + i));
            }
        }
        enterNode = getEnterNode();

    }
    public int getNodeWidth() {
        switch(buildingType) {
            case CAFE: return 3;
            case APARTMENT: return 2; 
            case HOSPITAL: return 8;
            case HOUSE: return 1;
        }
        return -1;
    }
    public int getNodeHeight() {
        switch(buildingType) {
            case CAFE: return 2;
            case APARTMENT: return 2; 
            case HOSPITAL: return 5;
            case HOUSE: return 1;
        }
        return -1;
    }
    public Node getEnterNode() {
        int x = this.nodes[0][0].getPosition().x + getNodeWidth() / 2;
        int y = this.nodes[0][0].getPosition().y - 1;
        Node node = map.getNodeAtPosition(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }
        y = this.nodes[0][0].getPosition().y - getNodeHeight() + 1;
        node = map.getNodeAtPosition(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }
        y = this.nodes[0][0].getPosition().y + getNodeHeight() / 2;
        x = this.nodes[0][0].getPosition().x - 1;
        node = map.getNodeAtPosition(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }
        x = this.nodes[0][0].getPosition().x + getNodeWidth() + 1;
        node = map.getNodeAtPosition(new Point(x, y));
        if(node instanceof PathfindNode) {
            return node;
        }

        return null;
    }
    public void enter(Person p) {
        
    }
    public void exit(Person p) {
        
    }
}