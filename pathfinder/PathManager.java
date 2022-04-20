package pathfinder;

import main.Updatable;
import main.Map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class PathManager implements Updatable {
    
    private Map map;
    private ArrayList<PathRequest> requestedPaths;
    class PathRequest 
    {
        PathRequest(Object requester, Point startPosition, Point targetPosition) 
        {
            this.requester = requester;
            this.startPosition = startPosition;
            this.targetPosition = targetPosition;
        }

        Object requester;
        Point startPosition, targetPosition;
    }

    public PathManager (Map map) 
    {
        this.map = map;
        requestedPaths = new ArrayList<PathRequest>();
    }

    public void requestPath(Object requester, Point startPosition, Point targetPosition)
    {
        requestedPaths.add(new PathRequest(requester,startPosition,targetPosition));
    }

    private void processFirstPath() 
    {
        findPath(requestedPaths.get(0).startPosition, requestedPaths.get(0).targetPosition);
        requestedPaths.remove(0);
        
    }

    private Object[] findPath (Point startPosition, Point targetPosition)
    {
        PathfindNode startNode = (PathfindNode)map.getNodeAtPosition(startPosition);
        PathfindNode targetNode = (PathfindNode)map.getNodeAtPosition(targetPosition);

        startNode.set_H_Cost((int)(10*startNode.getPosition().distance(targetNode.getPosition())));
        

        Heap<PathfindNode> openNodes = new Heap<PathfindNode>();
        ArrayList<PathfindNode> closedNodes = new ArrayList<PathfindNode>();

        openNodes.add (startNode);

        while (true) 
        {
            PathfindNode current = openNodes.get(0);

            openNodes.remove(0);
            closedNodes.add(current);

            if (current == targetNode)
                break;

            Node[] neighbourNodes = map.getNeighboursOf(current);

            for (int i = 0; i < neighbourNodes.length; i++) {
                
                if (neighbourNodes[i] == null)
                    continue;

                if (neighbourNodes[i] instanceof ImpassibleNode || closedNodes.contains(neighbourNodes[i]))
                    continue;

                PathfindNode neighbour = (PathfindNode) neighbourNodes[i];
                int distance = i == 0 || i == 2 || i == 5 || i == 7 ? 14 : 10; //012
                                                                               //3x4 where x is current node
                                                                               //567
                boolean isOpen = openNodes.contains(neighbour);
                if (neighbour.get_G_Cost() > current.get_G_Cost() + distance || !isOpen) 
                {
                    if (isOpen)
                        openNodes.remove (neighbour.getHeapIndex());
                   
                    else 
                    {
                        neighbour.set_H_Cost((int)(10*neighbour.getPosition().distance(targetNode.getPosition())));
                        neighbour.state = PathfindNode.State.OPEN;
                    }
                    
                    neighbour.setParent(current, distance);
                    openNodes.add(neighbour);
                        

                }

                
            }

        }


        ArrayList<PathfindNode> path = new ArrayList<PathfindNode>();
        PathfindNode traceBackNode = targetNode;
        while (traceBackNode != null) 
        {
            path.add(traceBackNode);
            traceBackNode = traceBackNode.getParent();
        }

        Collections.reverse(path);
        return path.toArray();
    }

    @Override
    public boolean hasFullyUpdated() {
        return requestedPaths.size() == 0;
    }

    @Override
    public void run() {
        processFirstPath();
    }
}


