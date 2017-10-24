package nl.mitchvanwijngaarden.peilmijnfitness.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mitch on 10/10/2017.
 */

public enum AuthenticatedUser {
    INSTANCE;

    private User currentUser;
    private DatabaseReference mDatabase;
    private SharedPreferences mPrefs;
    private Context context;

    public User getCurrentUser(){
        return this.currentUser;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }
}
