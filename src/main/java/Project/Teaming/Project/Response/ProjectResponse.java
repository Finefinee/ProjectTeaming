package Project.Teaming.Project.Response;

import Project.Teaming.Project.Entity.Project;

public record ProjectResponse(
        String title, //Id
        String content,
        String projectManager,
        String projectMember // 학번

) {
    public static Project.Teaming.Project.Response.ProjectResponse of (Project project) {
        return new Project.Teaming.Project.Response.ProjectResponse(
                project.getTitle(),
                project.getContent(),
                project.getProjectManager(),
                project.getProjectMember()
        );
    }

}
