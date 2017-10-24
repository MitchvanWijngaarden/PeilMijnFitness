package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mitch on 10/23/2017.
 */

public class Progress {
    private double weightLifted;


    private Date cDate;
    private String fDate = "";

    public void setWeightLifted(double weightLifted){
        this.weightLifted = weightLifted;
    }
    public double getWeightLifted(){
        return this.weightLifted;
    }

    public String toString(){
        if(cDate!= null)
            fDate = new SimpleDateFormat("dd-MM-yyyy").format(cDate);

        return fDate + " - " + this.weightLifted + "kg";
    }
    public void setDateOfToday(){
        this.cDate = new Date();
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
    }

}
