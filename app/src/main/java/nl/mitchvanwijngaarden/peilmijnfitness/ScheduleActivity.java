package nl.mitchvanwijngaarden.peilmijnfitness;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Excercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

public class ScheduleActivity extends MainActivity {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddButtonPress();
            }
        });

        populateListView();
    }

    private void handleAddButtonPress() {
    }

    private void populateListView() {
        final ListView list = (ListView) findViewById(R.id.schedulelist);

        if(currentUser.getSchedules() != null && !currentUser.getSchedules().isEmpty()) {
            ArrayAdapter<Schedule> adapter = new ArrayAdapter<Schedule>(
                    this, R.layout.listview_item, currentUser.getSchedules());


            list.setAdapter(adapter);

            list.setClickable(true);
            list.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                    Schedule s = (Schedule) list.getItemAtPosition(position);
                    Intent i = new Intent(ScheduleActivity.this, ScheduleDetailsActivity.class);
                    i.putExtra("CURRENTLY_SELECTED_SCHEDULE", (Serializable) s);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}
