package Project.Teaming.Invite.Controller;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping("/invite")
    public ResponseEntity<Void> sendInvite(@RequestBody InviteRequestDto dto, Principal principal) {
        String currentUsername = principal.getName(); // 로그인 유저의 username
        inviteService.sendInvite(currentUsername, dto);
        return ResponseEntity.ok().build();
    }

}
