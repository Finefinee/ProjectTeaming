package project.teaming.project.dto;

import project.teaming.project.entity.Project;

public record ProjectResponse(
        String title, //Id
        String content,
        String projectManager,
        String projectMember // 학번

) {
    public static ProjectResponse of (Project project) {
        return new ProjectResponse(
                project.getTitle(),
                project.getContent(),
                project.getProjectManager(),
                project.getProjectMember().toString()
        );
    }

}
