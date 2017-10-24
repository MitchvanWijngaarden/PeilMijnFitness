package nl.mitchvanwijngaarden.peilmijnfitness.Models;

/**
 * Created by Mitch on 10/23/2017.
 */

public class Progress {
    private double weightLifted;

    public void setWeightLifted(double weightLifted){
        this.weightLifted = weightLifted;
    }
    public double getWeightLifted(){
        return this.weightLifted;
    }

    public String toString(){
        return this.weightLifted + "kg";
    }
}
