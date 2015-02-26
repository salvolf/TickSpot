package tickspot.application.sev.tickspot.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.inject.Inject;

import roboguice.fragment.RoboDialogFragment;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.ProjectsSpinnerAdapter;
import tickspot.application.sev.tickspot.adapters.TasksSpinnerAdapter;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;

public class AddPresetDialog extends RoboDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @Inject
    private MyDatabaseHelper databaseHelper;

    private Button savePreset;
    private Button cancel;
    private Spinner projectsSpinner;
    private Spinner tasksSpinner;
    private ProjectsSpinnerAdapter projectAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        projectAdapter = new ProjectsSpinnerAdapter(view.getContext(), 0, databaseHelper.getProjectsSortedByClient());
        projectsSpinner.setAdapter(projectAdapter);
        savePreset.setOnClickListener(this);
        cancel.setOnClickListener(this);
        projectsSpinner.setOnItemSelectedListener(this);
        tasksSpinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == savePreset.getId()) {
            //TODO SET INTO THE DATABASE THE PRESET
            getDialog().cancel();
        } else if (view.getId() == cancel.getId()) {
            getDialog().cancel();
        } else if (view.getId() == projectsSpinner.getId()) {
            if (projectsSpinner.getSelectedItemPosition() != 0) {
                tasksSpinner.setEnabled(true);
                tasksSpinner.setAdapter(new TasksSpinnerAdapter(view.getContext(), 0, databaseHelper.getTasksRelatedToProject(databaseHelper.getProjectIdByName(String.valueOf(projectsSpinner.getSelectedItem())))));
            } else if (projectsSpinner.getSelectedItemPosition() == 0) {
                tasksSpinner.setEnabled(false);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == projectsSpinner.getId()) {
            projectAdapter.setHasItemSelected(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
