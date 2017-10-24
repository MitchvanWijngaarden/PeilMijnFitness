package nl.mitchvanwijngaarden.peilmijnfitness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.mitchvanwijngaarden.peilmijnfitness.R;

/**
 * Created by Mitch on 10/23/2017.
 */

public class WorkoutExerciseProgressFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progresschart, container, false);

        return view;
    }
}
