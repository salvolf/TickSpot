package tickspot.application.sev.tickspot.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

import com.google.inject.Inject;

import roboguice.fragment.RoboDialogFragment;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.ProjectsSpinnerAdapter;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.managers.ResponsesManager;

public class AddPresetDialog extends RoboDialogFragment implements View.OnClickListener {

    @Inject
    private MyDatabaseHelper databaseHelper;

    @Inject
    private ResponsesManager responsesManager;

    private Button savePreset;
    private Button cancel;
    private Spinner projectsSpinner;
    private Spinner tasksSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_preset, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);

        savePreset = (Button) view.findViewById(R.id.button_save);
        cancel = (Button) view.findViewById(R.id.button_cancel);
        projectsSpinner = (Spinner) view.findViewById(R.id.spinner_projects_dialog);
        tasksSpinner = (Spinner) view.findViewById(R.id.spinner_tasks_dialog);
       projectsSpinner.setAdapter(new ProjectsSpinnerAdapter(view.getContext(),0,responsesManager.getProjects()));
        //tasksSpinner.setAdapter(new ProjectsSpinnerAdapter(view.getContext(), 0, responsesManager.getTasks()));
        savePreset.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == savePreset.getId()) {
            //TODO SET INTO THE DATABASE THE PRESET
            getDialog().cancel();
        } else if (view.getId() == cancel.getId()) {
            getDialog().cancel();
        }
    }
}
