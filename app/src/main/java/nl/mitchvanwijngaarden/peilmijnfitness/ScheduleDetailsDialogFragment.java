package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Exercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/22/2017.
 */
public class ScheduleDetailsDialogFragment extends DialogFragment {
    private ScheduleDetailsFragment rootFragment;
    private Button dismissButton, addButton;
    private EditText nameExerciseField, repsExerciseField, setsExerciseField;
    private User currentUser;
    private DatabaseReference mDatabase;
    private Schedule schedule;

    public void setRootFragment(ScheduleDetailsFragment rootFragment) {
        this.rootFragment = rootFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_excercise, container, false);
        getDialog().setTitle("Nieuwe oefening");

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        nameExerciseField = (EditText) rootView.findViewById(R.id.exerciseNameInput);
        repsExerciseField = (EditText) rootView.findViewById(R.id.exerciseRepInput);
        setsExerciseField = (EditText) rootView.findViewById(R.id.exerciseSetsInput);

        dismissButton = (Button) rootView.findViewById(R.id.dismiss_exercise);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();;
            }
        });

        addButton = (Button) rootView.findViewById(R.id.add_exercise);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise e = new Exercise();
                e.setName(nameExerciseField.getText().toString());
                e.setReps(Integer.parseInt(repsExerciseField.getText().toString()));
                e.setSets(Integer.parseInt(setsExerciseField.getText().toString()));

                currentUser.getSchedule(schedule).addExcercise(e);
                rootFragment.notifyAdapter();

                if(!currentUser.getIsOffline()){
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Log.d("Debug ", "onComplete");
                            dismiss();
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
                    dismiss();
                }
            }
        });

        return rootView;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
