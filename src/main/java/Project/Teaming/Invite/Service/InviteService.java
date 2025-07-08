package Project.Teaming.Invite.Service;

import Project.Teaming.Invite.Dto.InviteRequestDto;

import java.security.Principal;

public interface InviteService {
    void sendInvite(String managerUsername, InviteRequestDto dto);
}
