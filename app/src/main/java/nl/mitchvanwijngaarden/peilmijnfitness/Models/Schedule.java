package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;


@IgnoreExtraProperties
public class Schedule {
    private String name;
    private ArrayList<Excercise> excercises;
    private boolean isActive;

    public Schedule(){
        this.excercises = new ArrayList<>();
    }

    public void addExcercise(Excercise excercise){
        excercises.add(excercise);
    }
    public void removeEcercise(Excercise excercise){
        excercises.remove(excercise);
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
}
