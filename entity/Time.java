package entity;

import javax.swing.JFrame;
import javax.swing.JLabel;

import main.GameData;
import main.Updatable;

/**
 * Time
 */
public class Time implements Updatable{

    public int day;
    public int hour; 
    public int minute;

    public static final int TIME_FACTOR = 41;

    long lasttime = System.currentTimeMillis()/41;
    long now;
    long delta;
    public Time() {
        GameData.updateManager.addUpdatable(this);
    }


    public static void main(String[] args) {
        
        Time a = new Time();
        a.setMinute();
    }
    public void setMinute() {
        while(true){
            if(minute<10)
                System.out.println((hour + ": 0" + minute));
            else
                System.out.println((hour + ": " + minute));
            now = System.currentTimeMillis()/41;
            delta = now - lasttime;
            minute += delta;
            if(minute>59)
            {
                minute = 0; 
                hour++;
            }
            if(hour>23)
            {
                hour = 0; 
                
            }
                
            lasttime = now;
            
        }
    }
    @Override
    public void run() {

            now = System.currentTimeMillis()/41;
            delta = now - lasttime;
            minute += delta;
            if(minute>59)
            {
                minute = 0; 
                hour++;
            }
            if(hour>23)
            {
                hour = 0; 
                day++;
                
            }
                
            lasttime = now;

        }
        
    @Override
    public boolean hasFullyUpdated() {
        return true;
    }
    @Override
    public void reset() {
        now = System.currentTimeMillis()/41;
        delta = now - lasttime;
        minute += delta;
        if(minute>59)
        {
            minute = 0; 
            hour++;
        }
        if(hour>23)
        {
            hour = 0; 
            
        }
            
        lasttime = now;
        
    }
    public long getTotalMinutes() {
        return day * 24 * 60 + hour * 60 + minute;
    }
    public long getTotalHours() {
        return day * 24 + hour;
    }


}
