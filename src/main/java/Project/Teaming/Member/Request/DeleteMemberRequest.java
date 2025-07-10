package Project.Teaming.Member.Request;

public record DeleteMemberRequest(
        int id,
        String username,
        String name,
        String password,
        int userId
) {
}
