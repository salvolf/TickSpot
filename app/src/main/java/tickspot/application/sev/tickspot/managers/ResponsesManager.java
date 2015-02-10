package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;

/**
 * Created by Sev on 08/02/15.
 */
public interface ResponsesManager {
    void setProjects(List<ProjectOrTasks> projects);
    void setTasks(List<ProjectOrTasks> tasks);
    void setClients(List<Client> clients);

    List<ProjectOrTasks> getProjects();
    List<ProjectOrTasks> getTasks();
    List<Client> getClients();

    // void setTasks(List<Task>projects);

}
