package Project.Teaming.Invite.Dto;

import lombok.Data;

@Data
public class InviteResponseDto {

    private Integer id;
    private String projectManagerUsername;
    private String projectMemberUsername;
    private boolean accepted;
}
