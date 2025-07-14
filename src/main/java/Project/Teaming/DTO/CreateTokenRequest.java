package Project.Teaming.DTO;

public record CreateTokenRequest(
        String username,
        String name,
        String email,
        int class_code//,
//        String role
) {
}