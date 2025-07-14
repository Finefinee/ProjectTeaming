package Project.Teaming.Invite.Service.Impl;

import Project.Teaming.Invite.Dto.AcceptInviteRequestDto;
import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Entity.Invite;
import Project.Teaming.Invite.Exception.InviteNotFoundException;
import Project.Teaming.Invite.Exception.NotInviteOwnerException;
import Project.Teaming.Invite.Repository.InviteRepository;
import Project.Teaming.Member.Entity.Member;
import Project.Teaming.Member.Exception.MemberNotFoundException;
import Project.Teaming.Member.Interface.MemberRepository;
import Project.Teaming.Project.Entity.Project;
import Project.Teaming.Project.Interface.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements Project.Teaming.Invite.Service.InviteService {

    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public void sendInvite(@AuthenticationPrincipal UserDetails userDetails, InviteRequestDto inviteRequestDto) {
        // 사람 찾기
        Member projectManager = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new MemberNotFoundException("프로젝트 팀장 없음"));
        Member projectMember = memberRepository.findByUsername(inviteRequestDto.getProjectMemberUsername())
                .orElseThrow(() -> new MemberNotFoundException("프로젝트 멤버 (초대되는 사람) 없음"));

        // 생성
        Invite invite = new Invite();
        invite.setProjectManager(projectManager);
        invite.setProjectMember(projectMember);
        invite.setAccepted(false);

        // 저장
        inviteRepository.save(invite);
    }

    @Override
    @Transactional
    public void acceptInvite(UserDetails userDetails, AcceptInviteRequestDto dto) {
        Invite invite = inviteRepository.findById(dto.getInviteId())
                .orElseThrow(() -> new InviteNotFoundException("초대가 존재하지 않습니다."));

        // UserDetails에서 username 꺼내기
        String username = userDetails.getUsername();

        if (!invite.getProjectMember().getUsername().equals(username)) {
            throw new NotInviteOwnerException("본인만 초대를 수락할 수 있습니다.");
        }

        invite.setAccepted(true);

        // 1. Invite에서 Project 가져오기 (Invite 엔티티에 Project가 있어야 함)
        Project project = invite.getProject(); // Invite에 getProject() 있어야 함

        // 2. 프로젝트의 멤버 리스트에 추가
        project.getProjectMember().add(invite.getProjectMember());

        // 3. 저장
        inviteRepository.save(invite);
        // 반드시 프로젝트도 저장
        projectRepository.save(project);
    }
}