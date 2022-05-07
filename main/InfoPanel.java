package main;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

import entity.Time;

public class InfoPanel extends JPanel implements Updatable{
    JLabel time;
    JLabel livingAmount;
    JLabel deadAmount;
    JLabel virusAmount;
    JLabel fps;

    //pop
    JPanel popPanel;
    JLabel avgAge;
    JLabel avgAwareness;
    JLabel avgVaccination;

    //virus
    JPanel virusPanel;
    JLabel contagiousness;
    JLabel mortality;
    JLabel asymptomaticRate;

    public InfoPanel() {
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setLayout(new BorderLayout());

        JPanel generalInfo = new JPanel();
        this.add(generalInfo, BorderLayout.CENTER);

        livingAmount = new JLabel();
        generalInfo.add(livingAmount, BorderLayout.CENTER);

        deadAmount = new JLabel();
        generalInfo.add(deadAmount, BorderLayout.CENTER);

        virusAmount = new JLabel();
        generalInfo.add(virusAmount, BorderLayout.CENTER);


        time = new JLabel();
        generalInfo.add(time, BorderLayout.CENTER);

        fps = new JLabel();
        this.add(fps, BorderLayout.EAST);

        JButton populationButton = new JButton("Population Info");
        JPanel thisJPanel = this;
        populationButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(popPanel == null){
                    popPanel = new JPanel();

                    avgAge = new JLabel();
                    popPanel.add(avgAge);

                    avgAwareness = new JLabel();
                    popPanel.add(avgAwareness);

                    avgVaccination = new JLabel();
                    popPanel.add(avgVaccination);

                    thisJPanel.add(popPanel, BorderLayout.SOUTH);
                }
                else {

                    
                    popPanel.remove(avgAge);
                    avgAge = null;
                    
                    popPanel.remove(avgAwareness);
                    avgAwareness = null;

                    popPanel.remove(avgVaccination);
                    avgVaccination = null;
                    
                    thisJPanel.remove(popPanel);
                    popPanel = null;
                }
                
            }
            
        });
        generalInfo.add(populationButton);

        JButton virusButton = new JButton("Virus info");
        virusButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(virusPanel == null){
                    virusPanel = new JPanel();

                    contagiousness = new JLabel();
                    virusPanel.add(contagiousness);

                    asymptomaticRate = new JLabel();
                    virusPanel.add(asymptomaticRate);

                    mortality = new JLabel();
                    virusPanel.add(mortality);

                    thisJPanel.add(virusPanel, BorderLayout.SOUTH);
                }
                else {

                    
                    virusPanel.remove(contagiousness);
                    contagiousness = null;
                    
                    virusPanel.remove(asymptomaticRate);
                    asymptomaticRate = null;

                    virusPanel.remove(mortality);
                    mortality = null;
                    
                    thisJPanel.remove(virusPanel);
                    virusPanel = null;
                }
                
            }
            
        });
        generalInfo.add(virusButton);


        this.setVisible(true);
        GameData.updateManager.addUpdatable(this);
    }

    @Override
    public void run() {
        livingAmount.setText("Population: " + GameData.getPersonAmount());

        deadAmount.setText("Dead: " + GameData.getDeadAmount());

        virusAmount.setText("Virus Amount: " + GameData.getVirusAmount());

        fps.setText( "FPS: " +String.format("%.2f", UpdateManager.getFps())); 

        String timeStr = "Day " + GameData.time.day + ", ";
        int hour = GameData.time.hour;
        int min = GameData.time.minute;
        if(hour < 10) {
            timeStr += "0";
        }
        timeStr += hour + ":";
        if(min < 10) {
            timeStr += "0";
        }
        timeStr += min;

        time.setText(timeStr);

        if(avgAge != null) {
            avgAge.setText(String.format("Avg. Age: %.1f", GameData.getAvgAge()));
        }
        if(avgAwareness != null) {
            avgAwareness.setText(String.format("Avg. Awareness: %%%.1f", GameData.getAvgAwareness()));
        }
        if(avgVaccination != null) {
            avgVaccination.setText(String.format("Vaccination Rate: %%%.1f", GameData.getVaccinatedRatio() * 100));
        }

        if(contagiousness != null) {
            contagiousness.setText(String.format("Contagiousness: %.1f", GameData.virus.getContagiousness()));
        }
        if(asymptomaticRate != null) {
            asymptomaticRate.setText(String.format("Asymptomatic Rate: %.1f", GameData.virus.getAsymptomaticRate()));
        }
        if(mortality != null) {
            mortality.setText(String.format("Mortality: %.1f", GameData.virus.getMortality()));
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
