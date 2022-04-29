package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

import pathfinder.Node;
import pathfinder.PathfindNode;
import pathfinder.PathfindNode.State;
import pathfinder.ImpassibleNode;

public class Map implements Drawable
{
    private int width, height, blockSize, gap;
    private Node[] nodes;

    public Map(int width, int height, int blockSize, int gap, String impassibleMap) {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        this.gap = gap;

        Main.gameData.drawManager.setBackground(Color.BLACK);
        Main.gameData.drawManager.setPreferredSize(new Dimension (blockSize + (width-1) * (blockSize+gap), blockSize + (height-1) * (blockSize+gap)));
        
        nodes = new Node[width * height];
        for (int j = 0; j < height; j++) {
            
            for (int i = 0; i < width; i++) {
                
                Point position = new Point (i,j);

                if (impassibleMap.charAt(i + j*width) == '0') 
                {
                    nodes [i + j*width] = new PathfindNode(position, PathfindNode.State.NORMAL);
                }

                else 
                {
                    nodes [i + j*width] = new ImpassibleNode(position);
                }
                
            }

        }
    }

    public Node getNodeAtPosition (Point position) 
    {
        if (position.x < 0 || position.x >= width)
            return null;
        
        if (position.y < 0 || position.y >= height)
            return null;

        return nodes [position.x + position.y * width];
    }

    public Point getPositionOfNode (Node node) 
    {
        return new Point ((int)node.getPosition().getX() * (blockSize + gap), (int)node.getPosition().getY() * (blockSize + gap));
    }

    public Node[] getNeighboursOf (Node node) 
    {
        Node[] neighbours = new Node[8];
        Point neighbourPosition = new Point (0,0);
        int index = 0;

        for (int i = 0; i < 3; i++) {
            
            neighbourPosition.y = (int)node.getPosition().getY() - 1 + i;
            for (int j = 0; j < 3; j++) {
               neighbourPosition.x = (int)node.getPosition().getX() - 1 + j;

               if (!neighbourPosition.equals(node.getPosition()))
                {
                    neighbours[index++] = getNodeAtPosition(neighbourPosition);
                }

            }
        }

        return neighbours;
    }
    public void setNodePassible(Point position) {
        Node node = getNodeAtPosition(position);
        if(node instanceof PathfindNode) {
            return;
        }
        nodes[position.x + position.y * width] = new PathfindNode(position, State.NORMAL);
    }
    public void setNodeImpassible(Point position) {
        Node node = getNodeAtPosition(position);
        if(node instanceof ImpassibleNode) {
            return;
        }
        nodes[position.x + position.y * width] = new ImpassibleNode(position);
    }
    
    public int getNodeWidth() {return this.width;}
    public int getNodeHeight() {return this.height;}
    public int getBlockSize() {return blockSize;}
    public int getGapSize() {return gap;}


    @Override
    public void paint(Graphics g) {
        
        for (Node node : nodes) 
        {
            if (node instanceof ImpassibleNode)
            {
                g.setColor (Color.BLACK);
            }

            else
                g.setColor (Color.WHITE);
            
            g.fillRect ((int)node.getPosition().getX() * (blockSize + gap), (int)node.getPosition().getY() * (blockSize + gap), blockSize, blockSize);
        }

        
    }
}