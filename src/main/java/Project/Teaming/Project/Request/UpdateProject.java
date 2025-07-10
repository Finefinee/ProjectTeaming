package Project.Teaming.Project.Request;

public record UpdateProject(
        Integer id,
        String title,
        String content,
        String projectManager,
        String projectMember
) {
}
