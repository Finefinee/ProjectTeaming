package project.teaming.project.dto;

import project.teaming.project.entity.Project;

public record ProjectResponse(
        Integer id,        // ID 필드 추가
        String title,
        String content,
        String projectManager,
        String projectMember
) {
    public static ProjectResponse of (Project project) {
        return new ProjectResponse(
                project.getId(),     // ID 추가
                project.getTitle(),
                project.getContent(),
                project.getProjectManager(),
                project.getProjectMember().toString()
        );
    }
}