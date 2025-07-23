package Project.Teaming.member.dto;

public record SignUpRequest(
        String username,
        String name,
        String password,
        String email,
        int class_code
) {
}
