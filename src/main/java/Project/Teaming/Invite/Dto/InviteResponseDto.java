package Project.Teaming.Invite.Dto;

public record InviteResponseDto (
        Integer id,
        String projectManagerUsername,
        String projectMemberUsername,
        boolean accepted
) {

}
