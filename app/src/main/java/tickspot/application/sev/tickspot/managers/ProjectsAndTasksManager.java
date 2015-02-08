package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Task;

/**
 * Created by Sev on 08/02/15.
 */
public interface ProjectsAndTasksManager {
    void setProjects(List<Project> projects);
    void setTasks(List<Task> tasks);

    List<Project> getProjects();
    List<Task> getTasks();

    // void setTasks(List<Task>projects);

}
