package Project.Teaming.member.dto;

public record CreateTokenRequest(
        String username,
        String name,
        String email,
        int class_code//,
//        String role
) {
}