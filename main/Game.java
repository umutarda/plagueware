package main;

import entity.*;
import pathfinder.*;

public class Game implements Updatable {
    private boolean isOver;
    private User user;


    public Game(User user) {
        this.user = user;
    }

    private void endGame() {
        isOver = true;
    }

    @Override
    public void run() {
        if(GameData.people.size() == 0) {
            endGame();
        }
    }
    
    @Override
    public boolean hasFullyUpdated() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
    }
    
}
