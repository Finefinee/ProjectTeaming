package Project.Teaming.Project.Controller;

import Project.Teaming.Project.Service.ProjectService;
import Project.Teaming.Project.dto.CreateProjectRequest;
import Project.Teaming.Project.dto.DeleteProjectRequest;
import Project.Teaming.Project.dto.UpdateProject;
import Project.Teaming.Project.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public void createProject(@RequestBody CreateProjectRequest request) {
        projectService.createProject(request);
    }

    @DeleteMapping
    public void deleteProject(@RequestBody DeleteProjectRequest request) {
        projectService.deleteProject(request);
    }


    @PatchMapping()
    public ProjectResponse updateProject (@RequestBody UpdateProject request) {
        return projectService.updateProject(request);
    }

    @GetMapping()
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

}
