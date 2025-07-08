package Project.Teaming.Invite.Dto;

import lombok.Data;

@Data
public class InviteRequestDto {

    private String projectManagerUsername;
    private String projectMemberUsername;
}