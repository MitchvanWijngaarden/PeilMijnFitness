package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import com.google.firebase.database.Exclude;
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

    @Exclude
    private int FORMAT_TYPE = 0;

    public void setFormatType(int formatType) {
        FORMAT_TYPE = formatType;
    }

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
        return displayToUI();
    }

    @Exclude
    private String displayToUI(){
        switch (FORMAT_TYPE) {
            case 0:
                return this.name + " " + reps + " repetitions, " + sets + " sets.";
            case 1:
                if(workoutProgressList.size() != 0) {
                    return this.name + " " + workoutProgressList.get(workoutProgressList.size() - 1).toString();
                } else {
                    return this.name;
                }
            default:
                return this.name;
        }
    }
}
