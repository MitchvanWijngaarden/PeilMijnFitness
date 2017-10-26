package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by Mitch on 10/23/2017.
 */

public class WorkoutExerciseDialogFragment extends DialogFragment{
    private WorkoutExerciseFragment rootFragment;
    private Button dismissButton, addButton;
    private EditText progressWeightField;
    private User currentUser;
    private Schedule schedule;
    private Exercise exercise;
    private SharedPreferences mPrefs;

    public void setRootFragment(WorkoutExerciseFragment rootFragment) {
        this.rootFragment = rootFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_progress, container, false);
        getDialog().setTitle("Toevoegen voortgang");

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        progressWeightField = (EditText) rootView.findViewById(R.id.weight_progress_input);

        dismissButton = (Button) rootView.findViewById(R.id.dismiss_progress);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();;
            }
        });

        addButton = (Button) rootView.findViewById(R.id.add_progress);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!progressWeightField.getText().toString().isEmpty()) {

                    Progress p = new Progress();
                    p.setWeightLifted(Double.parseDouble(progressWeightField.getText().toString()));
                    p.setDateOfToday();

                    currentUser.getSchedule(schedule).getExercise(exercise).addProgress(p);

                    rootFragment.notifyAdapter();

                    if (!currentUser.getIsOffline()) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Debug ", "onComplete");
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Debug ", "OnCancelled");
                            }
                        });
                    } else {
                        Gson gson = new Gson();
                        SharedPreferences prefs = getActivity().getSharedPreferences("USERACCOUNT", 0);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        String json = gson.toJson(currentUser); // myObject - instance of MyObject
                        prefsEditor.putString("user", json);
                        prefsEditor.commit();
                        dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Voer een gewicht in voor deze oefening.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }
    public void setSelectedExerciseAndSchedule(Exercise selectedExercise, Schedule selectedSchedule) {
        this.exercise = selectedExercise;
        this.schedule = selectedSchedule;
    }
}
