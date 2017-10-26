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
import android.widget.Button;
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
    private Button scheduleEditButton, scheduleRemoveButton;
    private ScheduleDetailsDialogFragment sddf;
    private ScheduleEditDialogFragment sedf;
    private AreYouSureDialogFragment aysdf;
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
        getActivity().setTitle(selectedSchedule.getName());

        exerciseList = (ListView) getView().findViewById(R.id.exerciseinschedulelist);
        scheduleEditButton = (Button) getView().findViewById(R.id.btn_edit_schedule);
        scheduleRemoveButton = (Button) getView().findViewById(R.id.btn_delete_schedule);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fm = getFragmentManager();
        sddf = new ScheduleDetailsDialogFragment();
        sddf.setRootFragment(this);
        sddf.setSchedule(selectedSchedule);

        sedf = new ScheduleEditDialogFragment();
        sedf.setSelectedSchedule(selectedSchedule);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddButtonPress();
            }
        });
        scheduleEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditButtonpress();
            }

        });

        aysdf = new AreYouSureDialogFragment();

        scheduleRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRemoveButtonpress();
            }
        });

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();
        populateListView();
    }

    private void handleRemoveButtonpress() {
        aysdf.setObjectToDelete(selectedSchedule);
        aysdf.setDismissText("Je raakt hiermee ook alle oefeningen en voortgang kwijt wat je in dit schema gezet hebt.");
        aysdf.show(fm, "Schedule remove");
    }

    private void handleAddButtonPress() {
        sddf.show(fm, "Exercise add dialog");
    }

    private void handleEditButtonpress() {
        sedf.show(fm, "Exercise edit dialog");
    }

    private void populateListView() {

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