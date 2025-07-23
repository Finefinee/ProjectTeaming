package Project.Teaming.invite.Service;

import Project.Teaming.invite.Dto.AcceptInviteRequestDto;
import Project.Teaming.invite.Dto.InviteRequestDto;
import Project.Teaming.invite.Entity.Invite;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface InviteService {
    void sendInvite(@AuthenticationPrincipal UserDetails userDetails, InviteRequestDto dto);
    void acceptInvite(UserDetails userDetails, AcceptInviteRequestDto dto);
    void refuseInvite(UserDetails userDetails, AcceptInviteRequestDto dto);
    List<Invite> getAllInvites();
    List<Invite> getAllInvitesByUsername(UserDetails userDetails);
}
