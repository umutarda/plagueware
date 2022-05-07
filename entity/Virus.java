package entity;

public class Virus {
    private double contagiousness;
    private double asymptomaticRate;
    private double mortality;

    public Virus(int contagiousness, int asymptomaticRate, int mortality) {
        this.contagiousness = contagiousness;
        this.asymptomaticRate = asymptomaticRate;
        this.mortality = mortality;
    }

    public double getContagiousness() {
        return this.contagiousness;
    }

    public void setContagiousness(double d) {
        this.contagiousness = d;
    }

    public double getAsymptomaticRate() {
        return this.asymptomaticRate;
    }

    public void setAsymptomaticRate(double asymptomaticRate) {
        this.asymptomaticRate = asymptomaticRate;
    }

    public double getMortality() {
        return this.mortality;
    }

    public void setMortality(Double mortality) {
        this.mortality = mortality;
    }
    
    
}
