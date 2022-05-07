package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.text.Position;

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

    public void setWindowSize() 
    {
        GameData.drawManager.setBackground(Color.BLACK);
        GameData.drawManager.setPreferredSize(new Dimension (blockSize + (width-1) * (blockSize+gap), blockSize + (height-1) * (blockSize+gap)));
        
    }
    public Node getNodeAtRowColumn (Point rowColumn) 
    {
        if (rowColumn.x < 0 || rowColumn.x >= width)
            return null;
        
        if (rowColumn.y < 0 || rowColumn.y >= height)
            return null;

        return nodes [rowColumn.x + rowColumn.y * width];
    }

    public Node getNodeAtPosition (Point position) 
    {
        return getNodeAtRowColumn(new Point((int) (position.getX() /  (blockSize + gap)), (int) (position.getY() /  (blockSize + gap))));
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
                    neighbours[index++] = getNodeAtRowColumn(neighbourPosition);
                }

            }
        }

        return neighbours;
    }

    public void setNodePassible(Point rowColumn) {
        Node node = getNodeAtRowColumn(rowColumn);
        if(node instanceof PathfindNode) {
            return;
        }
        nodes[rowColumn.x + rowColumn.y * width] = new PathfindNode(rowColumn, State.NORMAL);
    }
    public void setNodeImpassible(Point rowColumn) {
        Node node = getNodeAtRowColumn(rowColumn);
        if(node instanceof ImpassibleNode) {
            return;
        }
        nodes[rowColumn.x + rowColumn.y * width] = new ImpassibleNode(rowColumn);
    }
    
    public int getNodeWidth() {return this.width;}
    public int getNodeHeight() {return this.height;}
    public int getBlockSize() {return blockSize;}
    public int getGapSize() {return gap;}


    @Override
    public void paint(Graphics g) {
        
        for (Node node : nodes) 
        {
            
            g.setColor (node.getColor());
            
            g.fillRect ((int)node.getPosition().getX() * (blockSize + gap), (int)node.getPosition().getY() * (blockSize + gap), blockSize, blockSize);
        }

        
    }
}