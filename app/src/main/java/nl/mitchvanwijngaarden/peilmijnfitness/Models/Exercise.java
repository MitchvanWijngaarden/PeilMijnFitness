package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mitch on 10/8/2017.
 */

@IgnoreExtraProperties
public class Exercise {
    private String name;
    private int reps;
    private int sets;

    public Exercise(){}

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String toString(){
        return this.name + " " + reps + " repetitions, " + sets + " sets.";
    }
}
