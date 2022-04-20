import java.awt.Point;
import java.awt.Color;

public abstract class Node {

    private final Point POSITION;
    
    public Node(Point position) {
        this.POSITION = position;
    }

    public Point getPosition() 
    {
        return POSITION;
    }

    public abstract Color getColor();

}
