package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.DialogFragment;
import android.app.Fragment;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/22/2017.
 */

public class ScheduleDialogFragment extends DialogFragment{

    private Button dismissButton, addButton;
    private EditText nameScheduleField;
    private User currentUser;
    private ScheduleFragment rootFragment;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_schedule, container, false);
        getDialog().setTitle("Nieuw schema");

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        nameScheduleField = (EditText) rootView.findViewById(R.id.schedule_name_input);

        dismissButton = (Button) rootView.findViewById(R.id.dismiss_schedule);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();;
            }
        });

        addButton = (Button) rootView.findViewById(R.id.add_schedule);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule s = new Schedule();
                s.setName(nameScheduleField.getText().toString());

                currentUser.addSchedules(s);
                rootFragment.notifyAdapter();

                mDatabase = FirebaseDatabase.getInstance().getReference();


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




            }
        });

        return rootView;
    }

    public void setRootFragment(ScheduleFragment fragment){
        this.rootFragment = fragment;
    }
}
