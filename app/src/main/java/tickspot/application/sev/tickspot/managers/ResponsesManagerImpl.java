package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;

/**
 * Created by Sev on 08/02/15.
 */
public class ResponsesManagerImpl implements ResponsesManager {
    private List<ProjectOrTasks> projects;
    private List<ProjectOrTasks> tasks;
    private List<Client> clients;

    @Override
    public void setProjects(List<ProjectOrTasks> projects) {
        this.projects=projects;
    }

    @Override
    public void setTasks(List<ProjectOrTasks> tasks) {
        this.tasks=tasks;
    }

    @Override
    public void setClients(List<Client> clients) {
        this.clients=clients;
    }

    public List<ProjectOrTasks> getProjects() {
        return projects;
    }

    @Override
    public List<ProjectOrTasks> getTasks() {
        return tasks;
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }


    private  boolean areAllApiCallsDone(){
       // return (projects!=null && tasks != null);
        //TODO NEEDS TO BE CHANGED
        return true;
    }
}
