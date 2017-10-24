package nl.mitchvanwijngaarden.peilmijnfitness.Models;

/**
 * Created by Mitch on 10/10/2017.
 */

public enum AuthenticatedUser {
    INSTANCE;

    private User currentUser;

    public User getCurrentUser(){
        return this.currentUser;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }
}
