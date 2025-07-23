package Project1.Teaming1.member.dto;

public record SignUpRequest(
        String username,
        String name,
        String password,
        String email,
        int class_code
) {
}
