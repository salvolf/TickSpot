package tickspot.application.sev.tickspot.model;

import java.util.ArrayList;
import java.util.List;

import tickspot.application.sev.tickspot.restservice.models.ProjectResponse;

/**
 * Created by Sev on 03/02/15.
 */
public class ProjectList {
    private List<ProjectResponse> projects;

    public ProjectList(List<ProjectResponse> projects){
        this.projects=new ArrayList<>(projects);
    }
}
