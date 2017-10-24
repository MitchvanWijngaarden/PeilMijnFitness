package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.Schedule;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/22/2017.
 */

public class ScheduleFragment extends Fragment {

    private ListView scheduleList;
    private TextView listviewItem;
    private User currentUser;
    private FloatingActionButton fab;
    private ScheduleDialogFragment sdf;
    private FragmentManager fm;
    private ArrayAdapter<Schedule> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scheduleList = (ListView) getView().findViewById(R.id.schedulelist);

        fm = getFragmentManager();
        sdf = new ScheduleDialogFragment();
        sdf.setRootFragment(this);

        getActivity().setTitle("Schemas");
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
                sdf.show(fm, "Schedule add dialog");
            }
        });
    }

    private void populateListView() {
        if(currentUser.getSchedules() != null && !currentUser.getSchedules().isEmpty()) {
            adapter = new ArrayAdapter<Schedule>(
                    getActivity(), android.R.layout.simple_list_item_1, currentUser.getSchedules());

            scheduleList.setAdapter(adapter);

            scheduleList.setClickable(true);
            scheduleList.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                    Schedule s = (Schedule) scheduleList.getItemAtPosition(position);

                    ScheduleDetailsFragment scheduleDetailsFragment = new ScheduleDetailsFragment();
                    scheduleDetailsFragment.setSelectedSchedule(s);

                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_main, scheduleDetailsFragment);
                    ft.addToBackStack(null);
                    ft.commit();

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
}