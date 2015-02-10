package tickspot.application.sev.tickspot.model;

import java.util.ArrayList;
import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;

/**
 * Created by Sev on 08/02/15.
 */
public class TaskList {
    public List<ProjectOrTasks> tasks;
    public TaskList(List<ProjectOrTasks> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

}
