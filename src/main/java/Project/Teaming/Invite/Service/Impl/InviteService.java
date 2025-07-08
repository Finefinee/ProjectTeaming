package Project.Teaming.Invite.Service.Impl;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Entity.Invite;
import Project.Teaming.Invite.Repository.InviteRepository;
import Project.Teaming.Member.Member;
import Project.Teaming.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteService implements Project.Teaming.Invite.Service.InviteService {

    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void sendInvite(InviteRequestDto inviteRequestDto) {
        // 사람 찾기
        Member projectManager = memberRepository.findByUsername(inviteRequestDto.getProjectManagerUsername())
                .orElseThrow(() -> new RuntimeException("프로젝트 팀장 없음"));
        Member projectMember = memberRepository.findByUsername(inviteRequestDto.getProjectMemberUsername())
                .orElseThrow(() -> new RuntimeException("프로젝트 멤버 (초대되는 사람) 없음"));

        // 생성
        Invite invite = new Invite();
        invite.setProjectManager(projectManager);
        invite.setProjectMember(projectMember);
        invite.setAccepted(false);

        // 저장
        inviteRepository.save(invite);
    }
}