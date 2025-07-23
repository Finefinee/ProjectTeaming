package Project1.Teaming1.invite.dto;

public record InviteResponseDto (
        Integer id,
        String projectManagerUsername,
        String projectMemberUsername,
        boolean accepted
) {

}
