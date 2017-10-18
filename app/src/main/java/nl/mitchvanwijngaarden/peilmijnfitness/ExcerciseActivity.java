package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Excercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

public class ExcerciseActivity extends MainActivity {
    private User currentUser;
    private EditText inputExcercise;
    private ListAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView test = (TextView)header.findViewById(R.id.textView2);

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ExcerciseActivity.this);
            LayoutInflater inflater = getLayoutInflater();

            final View myLayout = inflater.inflate(R.layout.dialog_excercise, null);

            mBuilder.setView(myLayout)
                    .setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Excercise e = new Excercise();
                            inputExcercise = (EditText) myLayout.findViewById(R.id.exerciseInput);
                            e.setName(inputExcercise.getText().toString());
                            currentUser.addExcercise(e);
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
        });

        if(currentUser.getExcercises().size() != 0)
            populateListView();
    }

    private void populateListView(){
        final ListView list = (ListView) findViewById(R.id.exerciselist);

        ArrayAdapter<Excercise> adapter = new ArrayAdapter<Excercise>(
                this, R.layout.listview_item, currentUser.getExcercises());


        list.setAdapter(adapter);

        list.setClickable(true);
        list.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

//
//                Toast.makeText(getApplicationContext(), o.getName(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ExcerciseActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                final View myLayout = inflater.inflate(R.layout.dialog_excercise, null);


                mBuilder.setView(myLayout)
                        .setPositiveButton(R.string.edit_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Excercise e = (Excercise) list.getItemAtPosition(position);
                                inputExcercise = (EditText) myLayout.findViewById(R.id.exerciseInput);
                                e.setName(inputExcercise.getText().toString());
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                                mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser);
                                //dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });


                AlertDialog dialog = mBuilder.create();
                inputExcercise = (EditText) myLayout.findViewById(R.id.exerciseInput);
                inputExcercise.setText(list.getItemAtPosition(position).toString());
                dialog.show();
            }
        });
    }
}
