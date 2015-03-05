package tickspot.application.sev.tickspot.model;

import com.j256.ormlite.field.DatabaseField;

import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Task;

public class Preset {

    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_PROJECT = "project";
    public final static String COLUMN_NAME_TASK = "task";
    public final static String COLUMN_NAME_SUBSCRIPTION_ID = "subscription_id";

    @DatabaseField(columnName = COLUMN_NAME_ID, generatedId = true)
    public int generatedId;


    @DatabaseField(columnName = COLUMN_NAME_PROJECT, foreign = true)
    private Project project;

    @DatabaseField(columnName = COLUMN_NAME_TASK, foreign = true)
    private Task task;

    @DatabaseField(columnName = COLUMN_NAME_SUBSCRIPTION_ID)
    long  subscriptionId;

    @DatabaseField
    private int workingHours = -1;

    public Preset(){

    }

    public Preset(Project selectedProject, Task selectedTask, long selectedSubscriptionId, int selectedHours) {
        this.project = selectedProject;
        this.task = selectedTask;
        this.subscriptionId = selectedSubscriptionId;
        this.workingHours = selectedHours;
    }


    public String getFormattedPreset() {
        String projectString = this.project != null && this.project.name != null ? TickspotApplication.getContext().getString(R.string.project_string) + this.project.name : "";
        String taskString = this.task != null && this.task.name != null ? TickspotApplication.getContext().getString(R.string.task_string) + this.task.name : "";
        String workingHoursString = this.workingHours != -1 ? TickspotApplication.getContext().getString(R.string.hours_string) + this.workingHours : "";

        return projectString != "" ? projectString + " " : "" + taskString != "" ? taskString + " " : "" + workingHoursString != "" ? workingHoursString : "";
    }
}
