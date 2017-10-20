package nl.mitchvanwijngaarden.peilmijnfitness.Models;

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
    private ArrayList<Excercise> excercises;

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
            if (s.equals(schedule)) {
                return s;
            }
        }
        return null;
    }

    public void addSchedules(Schedule schedule){
        this.schedules.add(schedule);
    }

    public ArrayList<Excercise> getExcercises() {
        return excercises;
    }


    public void addExcercise(Excercise excercise){
        this.excercises.add(excercise);
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.schedules = new ArrayList<>();
        this.excercises = new ArrayList<>();
    }

    public void setAuthenticationID(String authenticationID){
        this.authenticationID = authenticationID;
    }

}