package tickspot.application.sev.tickspot.model;

import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.Task;

public class Preset {
    private Project project;
    private Task task;
    private Subscription subscription;
    int workingHours;

    public Preset(Project project, Task task, Subscription subscription, int hours) {
        this.project = project;
        this.task = task;
        this.workingHours = hours;
        this.subscription = subscription;
    }

    public String getFormattedPreset() {
        return "Project: " + project.name + " Task: " + task.name + "Hours: "+ workingHours;
    }
}
