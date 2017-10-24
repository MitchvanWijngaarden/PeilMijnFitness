package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        currentUser = AuthenticatedUser.INSTANCE.getCurrentUser();


        if(currentUser.getSchedules() != null) {
            scheduleAmountText.setText(currentUser.getSchedules().size() + " beschikbaar.");
        } else {
            scheduleAmountText.setText("0 beschikbaar.");
        }
    }
}
