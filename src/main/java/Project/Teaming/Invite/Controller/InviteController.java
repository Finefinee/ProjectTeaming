package Project.Teaming.Invite.Controller;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invite")
public class InviteController {

    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<Void> sendInvite(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody InviteRequestDto inviteRequestDto)
        {
            inviteService.sendInvite(userDetails, inviteRequestDto);
            return ResponseEntity.ok().build();
        }

}
