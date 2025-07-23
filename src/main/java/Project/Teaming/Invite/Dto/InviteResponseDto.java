package Project.Teaming.invite.Dto;

public record InviteResponseDto (
        Integer id,
        String projectManagerUsername,
        String projectMemberUsername,
        boolean accepted
) {

}
