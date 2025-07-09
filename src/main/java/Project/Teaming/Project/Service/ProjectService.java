package Project.Teaming.Project.Service;

import Project.Teaming.Project.Entity.Project;
import Project.Teaming.Project.Interface.ProjectRepository;
import Project.Teaming.Project.Request.CreateProjectRequest;
import Project.Teaming.Project.Response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectResponse CreateProject(CreateProjectRequest request) {
        Project project = Project.builder()
                .title(request.title())
                .content(request.content())
                .projectMember("asd")
                .projectManager("admin")
                .build();

        projectRepository.save(project);

        return new ProjectResponse(
              request.title(),
              request.content(),
                "admin",
                "asd"

        );
    }
}
