package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;


@IgnoreExtraProperties
public class Schedule {
    private String name;
    private ArrayList<Exercise> exercises;
    private boolean isActive;

    public Schedule(){
        this.exercises = new ArrayList<>();
    }

    public void addExcercise(Exercise exercise){
        exercises.add(exercise);
    }
    public void removeEcercise(Exercise exercise){
        exercises.remove(exercise);
    }

    public ArrayList<Exercise> getExercises(){
        return this.exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String toString(){
        if(this.isActive){
            return "✔ " + this.name;
        }
        return this.name;
    }

    public Exercise getExercise(Exercise exercise){
        for (Exercise e : exercises) {
            if (e.getName().equals(exercise.getName())) {
                return e;
            }
        }
        return null;
    }
}
