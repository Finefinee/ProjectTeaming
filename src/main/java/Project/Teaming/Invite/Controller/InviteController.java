package Project.Teaming.Invite.Controller;

import Project.Teaming.Invite.Dto.AcceptInviteRequestDto;
import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Dto.InviteResponseDto;
import Project.Teaming.Invite.Entity.Invite;
import Project.Teaming.Invite.Service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invites")
@CrossOrigin(origins = "*")
public class InviteController {

    private final InviteService inviteService;

    // 1. 초대 보내기 (POST /invites)
    @PostMapping
    public ResponseEntity<Void> sendInvite(
            @RequestBody InviteRequestDto inviteRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        inviteService.sendInvite(userDetails, inviteRequestDto);
        return ResponseEntity.ok().build();
    }

    // 2. 초대 수락 (POST /invites/accept)
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvite(
            @RequestBody AcceptInviteRequestDto acceptInviteRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        inviteService.acceptInvite(userDetails, acceptInviteRequestDto);
        return ResponseEntity.ok().build();
    }

    // 3. 초대 거절 (DELETE /invites/refuse)
    @DeleteMapping("/refuse")
    public ResponseEntity<Void> refuseInvite(
            @RequestBody AcceptInviteRequestDto acceptInviteRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        inviteService.refuseInvite(userDetails, acceptInviteRequestDto);
        return ResponseEntity.noContent().build(); // DELETE 요청에 적절한 응답
    }

    @GetMapping
    public ResponseEntity<List<InviteResponseDto>> getAllInvites() {
        List<Invite> invites = inviteService.getAllInvites();
        List<InviteResponseDto> inviteResponseDtos = invites.stream()
                .map(invite -> new InviteResponseDto(
                        invite.getId(),
                        invite.getProject().getProjectManager(),
                        invite.getProjectMember().getUsername(),
                        invite.isAccepted()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(inviteResponseDtos);
    }
}
