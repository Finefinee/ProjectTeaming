package Project.Teaming.project.dto;

public record DeleteProjectRequest(
        Integer id,
        String title,
        String content
) {
}
