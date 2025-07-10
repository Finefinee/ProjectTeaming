package Project.Teaming.Project.Controller;

import Project.Teaming.Project.Service.ProjectService;
import Project.Teaming.Project.Request.CreateProjectRequest;
import Project.Teaming.Project.Request.UpdateProject;
import Project.Teaming.Project.Response.ProjectResponse;
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

    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable int id) {
        projectService.deleteProjectById(id);
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
