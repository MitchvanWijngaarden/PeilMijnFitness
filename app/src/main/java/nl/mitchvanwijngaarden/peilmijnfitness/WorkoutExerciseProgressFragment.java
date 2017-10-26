package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Exercise;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Progress;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.R;

/**
 * Created by Mitch on 10/23/2017.
 */

public class WorkoutExerciseProgressFragment extends Fragment {
    private TabLayout tabLayout;
    private Schedule selectedSchedule;
    private Exercise selectedExercise;
    private LineChart chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progresschart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        chart = (LineChart) view.findViewById(R.id.chart);

        //currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();
        createTopTabBar();
        populateChartWithData();
    }

    private void populateChartWithData() {
        List<Entry> entries = new ArrayList<Entry>();

        int i = 0;
        if(!selectedExercise.getWorkoutProgressList().isEmpty()) {
            for (Progress p : selectedExercise.getWorkoutProgressList()) {

                float pWeight = (float) p.getWeightLifted();
                // turn your data into Entry objects
                entries.add(new Entry(i, pWeight));
                i++;
            }

            LineDataSet dataSet = new LineDataSet(entries, "Gewicht in kg."); // add entries to dataset

            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.getDescription().setEnabled(false);
            chart.invalidate(); // refresh
        }
    }

    private void createTopTabBar() {
        tabLayout.addTab(tabLayout.newTab().setText("Oefening"));
        tabLayout.addTab(tabLayout.newTab().setText("Voortgang"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0) {
                    WorkoutExerciseFragment fragment = new WorkoutExerciseFragment();
                    fragment.setSelectedExerciseAndSchedule(selectedExercise, selectedSchedule);
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else if(tabLayout.getSelectedTabPosition() == 1) {

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

    public void setSelectedExerciseAndSchedule(Exercise selectedExercise, Schedule selectedSchedule) {
        this.selectedExercise = selectedExercise;
        this.selectedSchedule = selectedSchedule;
    }
}
