package tickspot.application.sev.tickspot.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.List;

import roboguice.fragment.RoboDialogFragment;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.ProjectsSpinnerAdapter;
import tickspot.application.sev.tickspot.adapters.TasksSpinnerAdapter;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.model.Preset;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Task;

public class AddPresetDialog extends RoboDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @Inject
    private MyDatabaseHelper databaseHelper;

    private Button savePreset;
    private Button cancel;
    private Spinner projectsSpinner;
    private Spinner tasksSpinner;
    private ProjectsSpinnerAdapter projectAdapter;
    private TasksSpinnerAdapter taskAdapter;
    private int projectSpinnerOnItemSelectedTriggeredTimes = 0;
    private EditText workingHoursEditText;


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
        workingHoursEditText = (EditText) view.findViewById(R.id.hours_picker);
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

            if (TextUtils.isEmpty(workingHoursEditText.getText())) {
                workingHoursEditText.setError(getString(R.string.error_field_required));
                workingHoursEditText.requestFocus();
                return;
            }

            if (!projectAdapter.hasItemSelected() || !tasksSpinner.isSelected()) {
                Toast.makeText(getActivity(), getResources().getString(R.string.spinners_not_selected), Toast.LENGTH_SHORT).show();
                return;
            }

            Preset preset = new Preset((Project) projectsSpinner.getSelectedItem(), (Task) tasksSpinner.getSelectedItem(), Preferences.getClientSelectedId(), Integer.parseInt(String.valueOf(workingHoursEditText.getText())));
            databaseHelper.getPresetsDao().createIfNotExists(preset);

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
        projectAdapter.setHasItemSelected(true);

        if (parent.getId() == projectsSpinner.getId()) {
            projectSpinnerOnItemSelectedTriggeredTimes++;
            if (projectSpinnerOnItemSelectedTriggeredTimes > 1) {
                String projectSelectedName = String.valueOf(projectsSpinner.getSelectedItem());
                long projectId = databaseHelper.getProjectIdByName(projectSelectedName);
                List<Task> tasksRelatedToSelectedProject = databaseHelper.getTasksRelatedToProject(projectId);
                taskAdapter = new TasksSpinnerAdapter(view.getContext(), 0, tasksRelatedToSelectedProject);
                taskAdapter.setHasItemSelected(true);
                tasksSpinner.setAdapter(taskAdapter);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
