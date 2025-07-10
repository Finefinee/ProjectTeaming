package Project.Teaming.Invite.Service;

import Project.Teaming.Invite.Dto.AcceptInviteRequestDto;
import Project.Teaming.Invite.Dto.InviteRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface InviteService {
    void sendInvite(@AuthenticationPrincipal UserDetails userDetails, InviteRequestDto dto);
    public void acceptInvite(UserDetails userDetails, AcceptInviteRequestDto dto);
}
