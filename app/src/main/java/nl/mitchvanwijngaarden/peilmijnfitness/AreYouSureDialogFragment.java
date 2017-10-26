package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Exercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Progress;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/26/2017.
 */

public class AreYouSureDialogFragment extends DialogFragment{
    private Button dismissButton, confirmButton;
    private TextView dismissText;
    private User currentUser;
    private Object objectToDelete;
    private Schedule selectedSchedule;
    private Exercise selectedExercise;
    private String dismissTextString = "Weet je zeker dat je dit wilt verwijderen?";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.areyousure, container, false);

        dismissText = (TextView) view.findViewById(R.id.areyousure_text);
        dismissText.setText(dismissTextString);
        dismissButton = (Button) view.findViewById(R.id.dismiss_areyousure);
        confirmButton = (Button) view.findViewById(R.id.remove_areyousure);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();;
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

                if(objectToDelete instanceof Schedule){
                    currentUser.getSchedules().remove(objectToDelete);
                } else if(objectToDelete instanceof Exercise){
                    currentUser.getSchedule(selectedSchedule).getExercises().remove(objectToDelete);
                } else if(objectToDelete instanceof Progress){
                    currentUser.getSchedule(selectedSchedule).getExercise(selectedExercise).getWorkoutProgressList().remove(objectToDelete);
                }

                if(!currentUser.getIsOffline()){
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Log.d("Debug ", "onComplete");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Log.d("Debug ", "OnCancelled");
                        }
                    });
                } else{
                    Gson gson = new Gson();
                    SharedPreferences prefs = getActivity().getSharedPreferences("USERACCOUNT",0);
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    String json = gson.toJson(currentUser); // myObject - instance of MyObject
                    prefsEditor.putString("user", json);
                    prefsEditor.commit();
                }

                HomeFragment fragment = new HomeFragment();
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                dismiss();
            }
        });


        return view;
    }


    public void setSelectedSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public void setSelectedExercise(Exercise selectedExercise) {
        this.selectedExercise = selectedExercise;
    }


    public void setDismissText(String text){
        dismissTextString = dismissTextString  + text;
    }

    public void setObjectToDelete(Object objectToDelete) {
        this.objectToDelete = objectToDelete;
    }
}
