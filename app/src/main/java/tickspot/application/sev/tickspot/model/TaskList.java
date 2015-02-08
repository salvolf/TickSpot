package tickspot.application.sev.tickspot.model;

import java.util.ArrayList;
import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Task;

/**
 * Created by Sev on 08/02/15.
 */
public class TaskList {
    public List<Task> tasks;
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

}
