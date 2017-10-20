package nl.mitchvanwijngaarden.peilmijnfitness;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Excercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

public class ScheduleDetailsActivity extends MainActivity {
    private Schedule selectedSchedule;
    private EditText inputExercise;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        selectedSchedule = (Schedule) intent.getSerializableExtra("CURRENTLY_SELECTED_SCHEDULE");

        setTitle(selectedSchedule.getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddButtonPress();
            }
        });

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();
        populateListView();
    }

    private void handleAddButtonPress() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ScheduleDetailsActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        final View myLayout = inflater.inflate(R.layout.dialog_excercise, null);

        mBuilder.setView(myLayout)
                .setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Excercise e = new Excercise();
                        inputExercise = (EditText) myLayout.findViewById(R.id.exerciseNameInput);
                        e.setName(inputExercise.getText().toString());
                        currentUser.addExcercise(e);

                        currentUser.getSchedules().remove(selectedSchedule);
                        selectedSchedule.addExcercise(e);
                        currentUser.addSchedules(selectedSchedule);

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void populateListView() {
        final ListView list = (ListView) findViewById(R.id.exerciseinschedulelist);

        if(selectedSchedule.getExercises().size() != 0) {
            ArrayAdapter<Excercise> adapter = new ArrayAdapter<Excercise>(
                    this, R.layout.listview_item, selectedSchedule.getExercises());

            list.setAdapter(adapter);
            list.setClickable(true);
        }
    }
}