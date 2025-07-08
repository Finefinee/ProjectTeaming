package Project.Teaming.Invite.Service;

import Project.Teaming.Invite.Dto.InviteRequestDto;

public interface InviteService {
    void sendInvite(InviteRequestDto inviteRequestDto);
}
