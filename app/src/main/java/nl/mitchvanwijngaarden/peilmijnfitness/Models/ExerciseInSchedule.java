package nl.mitchvanwijngaarden.peilmijnfitness.Models;

/**
 * Created by Mitch on 10/18/2017.
 */

public class ExerciseInSchedule extends Excercise{

    private int repetitions;
    private int sets;

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    @Override
    public String toString(){
        return this.getName();
    }

}
