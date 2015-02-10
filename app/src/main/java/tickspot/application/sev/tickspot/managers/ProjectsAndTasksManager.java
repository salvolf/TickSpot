package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;

/**
 * Created by Sev on 08/02/15.
 */
public interface ProjectsAndTasksManager {
    void setProjects(List<ProjectOrTasks> projects);
    void setTasks(List<ProjectOrTasks> tasks);

    List<ProjectOrTasks> getProjects();
    List<ProjectOrTasks> getTasks();

    // void setTasks(List<Task>projects);

}
