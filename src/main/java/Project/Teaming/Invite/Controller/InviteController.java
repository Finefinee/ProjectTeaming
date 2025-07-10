package Project.Teaming.Invite.Controller;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping("/invite")
    public ResponseEntity<Void> sendInvite(@RequestBody InviteRequestDto dto,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        inviteService.sendInvite(userDetails, dto);
        return ResponseEntity.ok().build();
    }

}
