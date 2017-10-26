package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Exercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/26/2017.
 */

public class ProgressFragment extends Fragment {
    private ListView exerciseList;
    private User currentUser;
    private ArrayList<Exercise> exercises;
    private ArrayAdapter<Exercise> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progresslist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseList = (ListView) getView().findViewById(R.id.exerciseList);

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();
        exercises = new ArrayList<>();

        populateListView();

    }

    private void populateListView() {
        for(Schedule s : currentUser.getSchedules()){
            exercises.addAll(s.getExercises());
        }

        for(int i = 0;  i < exercises.size(); i++){
            exercises.get(i).setFormatType(1);
        }

        adapter = new ArrayAdapter<Exercise>(
                getActivity(), android.R.layout.simple_list_item_1, exercises);

        exerciseList.setAdapter(adapter);
        exerciseList.setClickable(true);
        exerciseList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                Exercise e = (Exercise) exerciseList.getItemAtPosition(position);

                WorkoutExerciseFragment workoutExerciseFragment = new WorkoutExerciseFragment();
                workoutExerciseFragment.setSelectedExerciseAndSchedule(e, null);

                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, workoutExerciseFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
