package Project.Teaming.Project.Request;

public record DeleteProjectRequest(
        Integer id,
        String title,
        String content
) {
}
