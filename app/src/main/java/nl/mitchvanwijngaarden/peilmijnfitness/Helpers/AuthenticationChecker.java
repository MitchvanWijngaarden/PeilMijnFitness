package nl.mitchvanwijngaarden.peilmijnfitness.Helpers;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nl.mitchvanwijngaarden.peilmijnfitness.LoginActivity;
import nl.mitchvanwijngaarden.peilmijnfitness.MainActivity;

/**
 * Created by Mitch on 10/1/2017.
 */

public class AuthenticationChecker {
    private boolean isAuth;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    public boolean isAuthenticated(){
        checkAuthentication();
        return isAuth;
    }


    protected void checkAuthentication() {

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    isAuth = true;
                    return;
                }
                isAuth = false;
            }
        };
    }
}
