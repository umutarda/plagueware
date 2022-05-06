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
    JLabel fps;

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

        fps = new JLabel();
        this.add(fps);


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
