package Project.Teaming.Project.Request;

public record UpdateProject(
        Long id,
        String title,
        String content,
        String projectManager,
        String projectMember
) {
}
