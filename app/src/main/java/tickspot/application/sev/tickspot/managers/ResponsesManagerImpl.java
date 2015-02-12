package tickspot.application.sev.tickspot.managers;

import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.Task;

/**
 * Created by Sev on 08/02/15.
 */
public class ResponsesManagerImpl implements ResponsesManager {
    private List<Subscription> subscriptions;
    private List<Project> projects;
    private List<Task> tasks;
    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    @Override
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

}
