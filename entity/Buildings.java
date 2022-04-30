package entity;

import java.awt.Color;
import java.util.ArrayList;

import main.GameData;
import main.Map;

public final class Buildings {
    public static final int ENTERTAINMENT = 0;
    public static final int HOME = 1;
    public static final int HOSPITAL = 2;

    public static class Cafe extends Building {

        public Cafe(int x, int y) throws Exception {
            super(x, y);
        }
        @Override
        public int getNodeWidth() {
            return 3;
        }
        @Override
        public int getNodeHeight() {
            return 2;
        }
        @Override
        public Color getColor() {
            return Color.BLUE;
        }
        @Override
        public int getBuildingType() {
            return ENTERTAINMENT;
        }
        
        
    }
    public static class Apartment extends Building {

        public Apartment(int x, int y) throws Exception {
            super(x, y);
        }

        @Override
        public int getNodeWidth() {
            return 2;
        }

        @Override
        public int getNodeHeight() {
            return 2;
        }

        @Override
        public Color getColor() {
            return Color.GRAY;
        }

        @Override
        public int getBuildingType() {
            return HOME;
        }
        
    }
    public static class Hospital extends Building {

        public Hospital(int x, int y) throws Exception {
            super(x, y);
            GameData.hospital = this;
        }

        @Override
        public int getNodeWidth() {
            return 8;
        }

        @Override
        public int getNodeHeight() {
            return 5;
        }

        @Override
        public Color getColor() {
            return Color.RED;
        }

        @Override
        public int getBuildingType() {
            return HOSPITAL;
        }

    }
    public static class House extends Building {

        public House(int x, int y) throws Exception {
            super(x, y);
        }

        @Override
        public int getNodeWidth() {
            return 1;
        }

        @Override
        public int getNodeHeight() {
            return 1;
        }

        @Override
        public Color getColor() {
            return Color.ORANGE;
        }

        @Override
        public int getBuildingType() {
            return HOME;
        }

    }
    
}
