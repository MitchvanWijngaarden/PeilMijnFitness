package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Exercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/22/2017.
 */

public class ScheduleDetailsFragment extends Fragment {

    private ListView exerciseList;
    private TextView listviewItem;
    private User currentUser;
    private FloatingActionButton fab;
    private ScheduleDetailsDialogFragment sddf;
    private FragmentManager fm;
    private ArrayAdapter<Exercise> adapter;
    private Schedule selectedSchedule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseList = (ListView) getView().findViewById(R.id.exerciseinschedulelist);

        fm = getFragmentManager();
        sddf = new ScheduleDetailsDialogFragment();
        sddf.setRootFragment(this);
        sddf.setSchedule(selectedSchedule);

        getActivity().setTitle(selectedSchedule.getName());
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sddf.show(fm, "Exercise add dialog");
            }
        });
    }

    private void populateListView() {

        Log.d("Schema", selectedSchedule.getExercises().toString());

            adapter = new ArrayAdapter<Exercise>(
                    getActivity(), android.R.layout.simple_list_item_1, currentUser.getSchedule(selectedSchedule).getExercises());

            exerciseList.setAdapter(adapter);

            exerciseList.setClickable(true);
            exerciseList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                Exercise e = (Exercise) exerciseList.getItemAtPosition(position);

                WorkoutExerciseFragment workoutExerciseFragment = new WorkoutExerciseFragment();
                workoutExerciseFragment.setSelectedExerciseAndSchedule(e, selectedSchedule);

                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, workoutExerciseFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

    }

    public void setSelectedSchedule(Schedule schedule){
        this.selectedSchedule = schedule;
    }

    public void notifyAdapter()  {
        getActivity().runOnUiThread(new Runnable()  {
            public void run() {
                if(adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}