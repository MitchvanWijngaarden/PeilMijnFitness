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


    public void addSchedules(Schedule schedule){
        this.schedules.add(schedule);
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.schedules = new ArrayList<>();
    }

    public void setAuthenticationID(String authenticationID){
        this.authenticationID = authenticationID;
    }

}