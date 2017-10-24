package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mitch on 10/8/2017.
 */

@IgnoreExtraProperties
public class Exercise{
    private String name;
    private int reps;
    private int sets;

    private ArrayList<Progress> workoutProgressList;

    public Exercise(){
        workoutProgressList = new ArrayList<>();
    }

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

    public ArrayList<Progress> getWorkoutProgressList() {
        return workoutProgressList;
    }

    public void setWorkoutProgressList(ArrayList<Progress> workoutProgressList) {
        this.workoutProgressList = workoutProgressList;
    }

    public void addProgress(Progress p){
        workoutProgressList.add(p);
    }

    public String toString(){
        return this.name + " " + reps + " repetitions, " + sets + " sets.";
    }


}
