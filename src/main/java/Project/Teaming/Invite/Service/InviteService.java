package Project.Teaming.Invite.Service;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface InviteService {
    void sendInvite(UserDetails userDetails, InviteRequestDto inviteRequestDto);
}
