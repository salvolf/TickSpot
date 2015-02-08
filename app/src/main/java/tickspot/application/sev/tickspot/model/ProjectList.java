package tickspot.application.sev.tickspot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.Project;

/**
 * Created by Sev on 03/02/15.
 */
public class ProjectList implements Serializable {
    public List<Project> projects;

    public ProjectList(List<Project> projects) {
        this.projects = new ArrayList<>(projects);
    }
}
