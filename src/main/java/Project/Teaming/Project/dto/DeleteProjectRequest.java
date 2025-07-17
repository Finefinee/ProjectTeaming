package Project.Teaming.Project.dto;

public record DeleteProjectRequest(
        Integer id,
        String title,
        String content
) {
}
