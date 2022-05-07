package main;

import javax.swing.JTextField;

import entity.SkillTree;
import entity.SkillTreeNode;

public class Savior extends Player implements Updatable{
    int lastDay = 0;

    public Savior(int initialBudget) {
        super(initialBudget);
        //TODO Auto-generated constructor stub
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
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
                    return "Find vaccination.\nCost: " + getCost();
                }
                
            }
        };
        SkillTree tree = new SkillTree(roots);
        return tree;
    }

    @Override
    protected void getPaid() {
        budget += GameData.getPersonAmount() * 5;
        budgetLabel.setText("Budget: " + budget);
        
        // skillPanel.add(new JTextField(""));
        
    }
    
}
