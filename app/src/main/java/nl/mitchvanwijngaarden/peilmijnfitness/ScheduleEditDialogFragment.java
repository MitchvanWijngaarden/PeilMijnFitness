package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.DialogFragment;
import android.app.Fragment;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/22/2017.
 */

public class ScheduleEditDialogFragment extends DialogFragment{

    private Button dismissButton, addButton;
    private EditText nameScheduleField;
    private User currentUser;
    private ScheduleFragment rootFragment;
    private DatabaseReference mDatabase;
    private Schedule selectedSchedule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_schedule, container, false);
        getDialog().setTitle("Bewerk schema");

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        nameScheduleField = (EditText) rootView.findViewById(R.id.schedule_name_input);
        nameScheduleField.setText(selectedSchedule.getName());

        dismissButton = (Button) rootView.findViewById(R.id.dismiss_schedule);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();;
            }
        });

        addButton = (Button) rootView.findViewById(R.id.add_schedule);
        addButton.setText("Bewerk");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameScheduleField.getText().toString().isEmpty()){

                    currentUser.getSchedule(selectedSchedule).setName(nameScheduleField.getText().toString());

                    if(!currentUser.getIsOffline()){
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                Log.d("Debug ", "onComplete");
                                dismiss();
                                ChangeToScheduleScreen();

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
                        ChangeToScheduleScreen();
                    }
                } else {
                    Toast.makeText(getActivity(), "Voer een naam in voor het schema.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    public void setSelectedSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    private void ChangeToScheduleScreen(){
        ScheduleFragment fragment = new ScheduleFragment();
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
