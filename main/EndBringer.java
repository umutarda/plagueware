package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import entity.*;

public class EndBringer extends Player implements Updatable{
    private int lastPersonAmount = GameData.getPersonAmount();
    private int lastDay = 0;
    private JTextField contagiousness;

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

        if(contagiousness == null) {
            contagiousness = new JTextField("contagiousness");
            contagiousness.setMaximumSize(new Dimension(skillPanel.getWidth(), skillPanel.getHeight() / 20));
            contagiousness.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int spent = Integer.parseInt(contagiousness.getText());
                        if(spent <= budget) {
                            budget -= spent;
                            if(GameData.virus.getContagiousness() + spent / 50.0 <= 100) {
                                GameData.virus.setContagiousness(GameData.virus.getContagiousness() + spent / 50.0);
                            }
                            else {
                                GameData.virus.setContagiousness(100);
                            }
                            budgetLabel.setText("Budget: " + budget);
                            skillPanel.remove(contagiousness);
                            contagiousness = null;
                        }
                    } catch (Exception ex) {
                    }
                    
                    
                }
                
            });

            skillPanel.add(contagiousness);
        }
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
