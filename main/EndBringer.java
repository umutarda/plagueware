package main;

import entity.*;

public class EndBringer extends Player implements Updatable{
    private int lastPersonAmount = GameData.getPersonAmount();
    private int lastDay = 0;

    public EndBringer(int initialBudget) {
        super(initialBudget);
        GameData.updateManager.addUpdatable(this);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected SkillTree getSkillTree() {
        SkillTreeNode[] roots = {
            new SkillTreeNode() {

                @Override
                protected void activateEvent() {
                    GameData.hospital.hasVaccine = true;
                    
                }

                @Override
                public int getCost() {
                    return 500;
                }

                @Override
                public String toString() {
                    return "Find vaccination. Cost: " + getCost();
                }
                
            }
        };
        SkillTree tree = new SkillTree(roots);
        return tree;
    }

    @Override
    protected void getPaid() {
        int currentPersonAmount = GameData.getPersonAmount();
        budget += (lastPersonAmount - currentPersonAmount) * 300;
        budgetLabel.setText("Budget: " + budget);
        lastPersonAmount = currentPersonAmount;
    }

    @Override
    public void run() {
        if(GameData.time.day != lastDay) {
            getPaid();
            lastDay = GameData.time.day;
        }
        
    }

    @Override
    public boolean hasFullyUpdated() {
        return false;
    }

    @Override
    public void reset() {
        
    }
    
}
