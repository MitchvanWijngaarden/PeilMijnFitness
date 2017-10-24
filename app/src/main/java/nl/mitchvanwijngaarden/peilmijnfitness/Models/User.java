package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class User {

    private String name;
    private String authenticationID;
    private int weight;
    private Date dateOfBirth;
    private ArrayList<Schedule> schedules;
    private ArrayList<Exercise> exercises;
    private boolean isOffline = false;

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    public boolean getIsOffline(){
        return this.isOffline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthenticationID() {
        return authenticationID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public Schedule getSchedule(Schedule schedule){
        for (Schedule s : schedules) {
            Log.d("Schedule names ", s.getExercises().toString());

            if (s.getName().equals(schedule.getName())) {
                return s;
            }
        }
        return null;
    }

    public void addSchedules(Schedule schedule){
        this.schedules.add(schedule);
    }

    public ArrayList<Exercise> getExcercises() {
        return exercises;
    }


    public void addExcercise(Exercise excercise){
        this.exercises.add(excercise);
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.schedules = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    public void setAuthenticationID(String authenticationID){
        this.authenticationID = authenticationID;
    }

}