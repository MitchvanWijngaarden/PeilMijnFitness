package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;


@IgnoreExtraProperties
public class Schedule implements Serializable {
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

    public ArrayList<Excercise> getExercises(){
        return this.excercises;
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
