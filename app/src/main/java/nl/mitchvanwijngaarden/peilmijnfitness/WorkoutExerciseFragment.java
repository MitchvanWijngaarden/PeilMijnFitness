package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Exercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Progress;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

public class WorkoutExerciseFragment extends Fragment {

    private ListView workoutExerciseList;
    private TextView listviewItem;
    private User currentUser;
    private FloatingActionButton fab;
    private WorkoutExerciseDialogFragment wedf;
    private FragmentManager fm;
    private Schedule selectedSchedule;
    private Exercise selectedExercise;
    private ArrayAdapter<Progress> adapter;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workoutExerciseList = (ListView) getView().findViewById(R.id.schedulelist);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        fm = getFragmentManager();
        wedf = new WorkoutExerciseDialogFragment();
        wedf.setRootFragment(this);
        wedf.setSelectedExerciseAndSchedule(selectedExercise, selectedSchedule);

        getActivity().setTitle(selectedExercise.getName());
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddButtonPress();
            }
        });

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();
        populateListView();
        createTopTabBar();
    }

    private void createTopTabBar() {
        tabLayout.addTab(tabLayout.newTab().setText("Oefening"));
        tabLayout.addTab(tabLayout.newTab().setText("Voortgang"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0) {
                }
                else if(tabLayout.getSelectedTabPosition() == 1) {
                    WorkoutExerciseProgressFragment fragment = new WorkoutExerciseProgressFragment();
                    fragment.setSelectedExerciseAndSchedule(selectedExercise, selectedSchedule);
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void handleAddButtonPress() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wedf.show(fm, "Exercise add progress dialog");
            }
        });
    }

    private void populateListView() {
        if(currentUser.getSchedules() != null && !currentUser.getSchedules().isEmpty()) {
            adapter = new ArrayAdapter<Progress>(
                    getActivity(), android.R.layout.simple_list_item_1, selectedExercise.getWorkoutProgressList());

            workoutExerciseList.setAdapter(adapter);

            workoutExerciseList.setClickable(true);

            workoutExerciseList.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {


                }
            });
        }
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

    public void setSelectedExerciseAndSchedule(Exercise selectedExercise, Schedule selectedSchedule) {
        this.selectedExercise = selectedExercise;
        this.selectedSchedule = selectedSchedule;
    }
}