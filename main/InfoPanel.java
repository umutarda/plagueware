package main;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import entity.Time;

public class InfoPanel extends JPanel implements Updatable{
    JLabel time;
    JLabel livingAmount;
    JLabel deadAmount;
    JLabel virusAmount;
    public InfoPanel() {
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));

        livingAmount = new JLabel();
        this.add(livingAmount);

        deadAmount = new JLabel();
        this.add(deadAmount);

        virusAmount = new JLabel();
        this.add(virusAmount);


        time = new JLabel();
        this.add(time);


        this.setVisible(true);
        GameData.updateManager.addUpdatable(this);
    }

    @Override
    public void run() {
        livingAmount.setText("Population: " + GameData.getPersonAmount());

        deadAmount.setText("Dead: " + GameData.getDeadAmount());

        virusAmount.setText("Virus Amount: " + GameData.getVirusAmount());

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
