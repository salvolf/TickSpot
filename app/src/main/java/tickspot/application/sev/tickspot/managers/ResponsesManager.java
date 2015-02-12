package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.Task;

/**
 * Created by Sev on 08/02/15.
 */
public interface ResponsesManager {
    void setProjects(List<Project> projects);
    void setSubscriptions(List<Subscription> subscriptions);
    void setTasks(List<Task> tasks);
    void setClients(List<Client> clients);

    List<Project> getProjects();
    List<Task> getTasks();
    List<Client> getClients();
    List<Subscription> getSubscriptions();

    // void setTasks(List<Task>projects);

}
