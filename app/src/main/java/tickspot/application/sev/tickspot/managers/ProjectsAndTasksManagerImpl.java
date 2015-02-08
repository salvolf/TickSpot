package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Task;

/**
 * Created by Sev on 08/02/15.
 */
public class ProjectsAndTasksManagerImpl implements ProjectsAndTasksManager {
    private List<Project> projects;
    private List<Task> tasks;

    @Override
    public void setProjects(List<Project> projects) {
        this.projects=projects;
    }

    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks=tasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    private  boolean areAllApiCallsDone(){
       // return (projects!=null && tasks != null);
        //TODO NEEDS TO BE CHANGED
        return true;
    }
}
