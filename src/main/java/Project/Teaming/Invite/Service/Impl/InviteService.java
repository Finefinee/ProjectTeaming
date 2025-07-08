package Project.Teaming.Invite.Service.Impl;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Entity.Invite;
import Project.Teaming.Invite.Repository.InviteRepository;
import Project.Teaming.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteService implements Project.Teaming.Invite.Service.InviteService {

    private final InviteRepository inviteRepository;

    @Override
    public void sendInvite(InviteRequestDto projectManagerDto, InviteRequestDto projectMemberDto) {
        System.out.println("please make MemberDto");
    }
}
