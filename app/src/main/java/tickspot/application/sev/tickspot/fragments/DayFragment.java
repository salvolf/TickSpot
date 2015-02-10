package tickspot.application.sev.tickspot.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.ProjectsSpinnerAdapter;
import tickspot.application.sev.tickspot.managers.ResponsesManager;

public class DayFragment extends RoboFragment {

    @Inject
    private ResponsesManager responsesManager;

    @InjectView(R.id.spinner_project)
    private Spinner spinnerProjects;

    @InjectView(R.id.spinner_task)
    private Spinner spinnerTasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerProjects.setAdapter(new ProjectsSpinnerAdapter(getActivity(),R.layout.spinner_row, responsesManager.getProjects()));
        //spinnerTasks.setAdapter(new ProjectsSpinnerAdapter(getActivity(),R.layout.spinner_row,));

    }
}
