package entity;

public class Virus {
    private int contagiousness;
    private int asymptomaticRate;
    private int mortality;

    public Virus(int contagiousness, int asymptomaticRate, int mortality) {
        this.contagiousness = contagiousness;
        this.asymptomaticRate = asymptomaticRate;
        this.mortality = mortality;
    }

    public int getContagiousness() {
        return this.contagiousness;
    }

    public void setContagiousness(int contagiousness) {
        this.contagiousness = contagiousness;
    }

    public int getAsymptomaticRate() {
        return this.asymptomaticRate;
    }

    public void setAsymptomaticRate(int asymptomaticRate) {
        this.asymptomaticRate = asymptomaticRate;
    }

    public int getMortality() {
        return this.mortality;
    }

    public void setMortality(int mortality) {
        this.mortality = mortality;
    }
    
    
}
