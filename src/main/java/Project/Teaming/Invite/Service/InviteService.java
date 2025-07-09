package Project.Teaming.Invite.Service;

import Project.Teaming.Invite.Dto.InviteRequestDto;

public interface InviteService {
    void sendInvite(String managerUsername, InviteRequestDto dto);
}
