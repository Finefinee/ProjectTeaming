package Project.Teaming.Member.Request;


public record CreateMemberRequest(
        String username,
        String name,
        String password,
        int class_code
    ) {
}
