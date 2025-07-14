package Project.Teaming.Member.Request;

public record UpdateMember(
        int id,
        String username,
        String name,
        String password,
        int class_code
) {
}
