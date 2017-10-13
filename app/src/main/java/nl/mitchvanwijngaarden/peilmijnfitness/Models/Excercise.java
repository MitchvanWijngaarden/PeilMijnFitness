package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mitch on 10/8/2017.
 */

@IgnoreExtraProperties
public class Excercise {
    private String name;

    public Excercise(){}

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
