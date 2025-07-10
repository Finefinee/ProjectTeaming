package Project.Teaming.Project.Service;

import Project.Teaming.Project.Request.CreateProjectRequest;
import Project.Teaming.Project.Response.ProjectResponse;

public interface ProjectService {
    public ProjectResponse CreateProject(CreateProjectRequest request);
}
