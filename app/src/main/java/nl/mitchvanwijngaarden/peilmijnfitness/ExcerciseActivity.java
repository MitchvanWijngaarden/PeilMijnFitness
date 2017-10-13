package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Excercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

public class ExcerciseActivity extends AppCompatActivity {
    private User currentUser;
    private EditText inputExcercise;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView)findViewById(R.id.excercises);


        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ExcerciseActivity.this);
            LayoutInflater inflater = getLayoutInflater();

            final View myLayout = inflater.inflate(R.layout.dialog_excercise, null);

            mBuilder.setView(myLayout)

                    // Add action buttons
                    .setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Excercise e = new Excercise();
                            inputExcercise = (EditText) myLayout.findViewById(R.id.exerciseInput);
                            e.setName(inputExcercise.getText().toString());
                            currentUser.addExcercise(e);
                            DatabaseReference mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference();

                            mDatabase.child("users").child(currentUser.getAuthenticationID()).setValue(currentUser);
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



        if(currentUser.getExcercises().size() != 0) {

            for(Excercise excercise : currentUser.getExcercises()){
                textView.setText(textView.getText().toString() + excercise.getName() + "\n");
            }
        } else{
            textView.setText("Er zijn nog geen oefeningen toegevoegd.");
        }

    }

}
