package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.mitchvanwijngaarden.peilmijnfitness.Models.AuthenticatedUser;
import nl.mitchvanwijngaarden.peilmijnfitness.Models.User;

/**
 * Created by Mitch on 10/22/2017.
 */

public class HomeFragment extends Fragment{
    private TextView scheduleAmountText;
    private CardView cv, cv2;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("PeillMijnFitness");
        scheduleAmountText = (TextView) view.findViewById(R.id.schedule_amount);
        cv = (CardView) view.findViewById(R.id.cv);
        cv2 = (CardView) view.findViewById(R.id.cv2);

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();

        if(currentUser.getSchedules() != null) {
            scheduleAmountText.setText(currentUser.getSchedules().size() + " beschikbaar.");
        } else {
            scheduleAmountText.setText("0 beschikbaar.");
        }

        cv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ScheduleFragment fragment = new ScheduleFragment();
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ProgressFragment fragment = new ProgressFragment();
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
