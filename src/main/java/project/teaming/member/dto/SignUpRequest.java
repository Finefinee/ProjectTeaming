package project.teaming.member.dto;

public record SignUpRequest(
        String username,
        String name,
        String password,
        String email,
        String class_code
) {
}
