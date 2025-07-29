package project.teaming.member.dto;

public record CreateTokenRequest(
        String username,
        String name,
        int grade//,
//        String role
) {
}