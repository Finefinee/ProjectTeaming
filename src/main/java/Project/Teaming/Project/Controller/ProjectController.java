package Project.Teaming.project.controller;

import Project.Teaming.project.Service.ProjectService;
import Project.Teaming.project.dto.CreateProjectRequest;
import Project.Teaming.project.dto.DeleteProjectRequest;
import Project.Teaming.project.dto.UpdateProject;
import Project.Teaming.project.dto.ProjectResponse;
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
