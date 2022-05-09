package main;

import entity.SkillTree;
import entity.SkillTreeNode;
import entity.Buildings.Hospital;

public class Savior extends Player implements Updatable{
    int lastDay = 0;
    int lastVacAmount = 0;

    public Savior(int initialBudget) {
        super(initialBudget);
        GameData.updateManager.addUpdatable(this);
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
        SkillTreeNode vac = new SkillTreeNode() {

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
                return "Find vaccination.(Cost: " + getCost() + ")";
            }
            
        };
        SkillTreeNode[] roots = {vac};
        SkillTreeNode freeVac = new SkillTreeNode() {

            @Override
            protected void activateEvent() {
                GameData.hospital.isVaccineFree = true;
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public String toString() {
                return "Make vaccination free. (Cost: " + getCost() + ")";
            }
            
        };
        SkillTreeNode paidVac = new SkillTreeNode() {

            @Override
            protected void activateEvent() {
                GameData.hospital.isVaccineFree = false;
                
            }

            @Override
            public int getCost() {
                return 0;
            }

            @Override
            public String toString() {
                return "Make vaccination paid. (Cost: " + getCost() + ")";
            }
            
        };
        freeVac.setMutuallyExclusive(paidVac);
        roots[0].addNextNode(freeVac);
        roots[0].addNextNode(paidVac);
        SkillTree tree = new SkillTree(roots);
        return tree;
    }

    @Override
    protected void getPaid() {
        budget += GameData.getPersonAmount() * 5;
        if(!GameData.hospital.isVaccineFree) {
            int currentVacAmount = GameData.getVaccinationAmount();
            budget += (currentVacAmount - lastVacAmount) * 15;
            lastVacAmount = currentVacAmount;
        }
        budgetLabel.setText("Budget: " + budget);
        
    }
    
}
