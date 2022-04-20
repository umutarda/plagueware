package entity.Buildings;

public abstract class Building {
    private Node position;
    private int width;
    private int height;

    public abstract boolean isOpen();
}
