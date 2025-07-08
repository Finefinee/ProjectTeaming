package Project.Teaming.Invite.Dto;

import lombok.Data;

@Data
public class InviteResponseDto {

    private Long id;
    private String projectManagerUsername;
    private String projectMemberUsername;
    private boolean accepted;
}
