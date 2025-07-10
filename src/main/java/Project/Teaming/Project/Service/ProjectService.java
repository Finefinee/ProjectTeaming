package Project.Teaming.Project.Service;

import Project.Teaming.Member.Request.UpdateMember;
import Project.Teaming.Project.Entity.Project;
import Project.Teaming.Project.Interface.ProjectRepository;
import Project.Teaming.Project.Request.CreateProjectRequest;
import Project.Teaming.Project.Request.DeleteProjectRequest;
import Project.Teaming.Project.Request.UpdateProject;
import Project.Teaming.Project.Response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectResponse createProject(CreateProjectRequest request) {
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
    public String deleteProject(DeleteProjectRequest request) {
        Project project = projectRepository.findById(request.id()).orElse(null);

        if (project == null) {
            throw new IllegalArgumentException(request.id()+"project not found");
        }
        projectRepository.delete(project);
        return "Project deleted";
    }

    public ProjectResponse findProjectById(int id) {
        Project e = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id+"project not found"));
//        if (e == null) {
//            throw new IllegalArgumentException("Project not found");
//        }
        return ProjectResponse.of(e);
    }

    public void deleteProjectById(int id) {
        Project e = projectRepository.findById(id).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException("Project not found");
        }
        projectRepository.delete(e);
    }

    public ProjectResponse updateProject(UpdateProject request) {
        Project e = projectRepository.findById(request.id()).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException("Project not found");
        }
        e.setId(request.id());

        projectRepository.save(e);

        return ProjectResponse.of(e);
    }

    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream().map(ProjectResponse::of).collect(Collectors.toList());
    }
}
