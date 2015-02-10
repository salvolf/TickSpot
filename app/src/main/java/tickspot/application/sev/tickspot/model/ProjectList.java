package tickspot.application.sev.tickspot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;

/**
 * Created by Sev on 03/02/15.
 */
public class ProjectList implements Serializable {
    public List<ProjectOrTasks> projects;

    public ProjectList(List<ProjectOrTasks> projects) {
        this.projects = new ArrayList<>(projects);
    }
}
