package Project1.Teaming1.invite.service;

import Project1.Teaming1.invite.dto.AcceptInviteRequestDto;
import Project1.Teaming1.invite.dto.InviteRequestDto;
import Project1.Teaming1.invite.entity.Invite;
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
