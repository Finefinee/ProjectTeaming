package Project.Teaming.Invite.Service.Impl;

import Project.Teaming.Invite.Dto.AcceptInviteRequestDto;
import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Entity.Invite;
import Project.Teaming.Invite.Exception.AlreadyProjectMemberException;
import Project.Teaming.Invite.Exception.InviteNotFoundException;
import Project.Teaming.Invite.Exception.NotInviteOwnerException;
import Project.Teaming.Invite.Repository.InviteRepository;
import Project.Teaming.Invite.Service.InviteService;
import Project.Teaming.Member.Entity.Member;
import Project.Teaming.Member.Exception.MemberNotFoundException;
import Project.Teaming.Member.Interface.MemberRepository;
import Project.Teaming.Project.Entity.Project;
import Project.Teaming.Project.Exception.ProjectNotFoundException;
import Project.Teaming.Project.Interface.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public void sendInvite(UserDetails userDetails, InviteRequestDto inviteRequestDto) {
        // 1. 프로젝트 팀장(로그인 유저) 조회
        Member projectManager = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new MemberNotFoundException("프로젝트 팀장 없음"));

        if (userDetails.getUsername() != projectRepository.findById(inviteRequestDto.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("프로젝트 없음"))
                .getProjectManager()
        ) {
            throw new NotInviteOwnerException("프로젝트 팀장만 초대할 수 있습니다.");
        }

        // 2. 초대받을 멤버 조회
        Member projectMember = memberRepository.findByUsername(inviteRequestDto.getProjectMemberUsername())
                .orElseThrow(() -> new MemberNotFoundException("프로젝트 멤버(초대 대상) 없음"));

        // 3. 프로젝트 조회
        Project project = projectRepository.findById(inviteRequestDto.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("프로젝트 없음"));

        // 4. 초대 객체 생성 및 저장
        Invite invite = new Invite();
        invite.setProjectManager(projectManager);
        invite.setProjectMember(projectMember);
        invite.setProject(project);
        invite.setAccepted(false);

        inviteRepository.save(invite);
    }

    @Override
    @Transactional
    public void acceptInvite(UserDetails userDetails, AcceptInviteRequestDto dto) {
        // 1. 초대 정보 조회
        Invite invite = inviteRepository.findById(dto.getInviteId())
                .orElseThrow(() -> new InviteNotFoundException("초대가 존재하지 않습니다."));

        // 2. 초대 수락 자격 확인 (본인만 수락 가능)
        String username = userDetails.getUsername();
        if (!invite.getProjectMember().getUsername().equals(username)) {
            throw new NotInviteOwnerException("본인만 초대를 수락할 수 있습니다.");
        }

        // 3. 초대 수락 처리
        invite.setAccepted(true);

        // 4. 프로젝트 팀원으로 등록 (중복 등록 방지)
        Project project = invite.getProject();
        Member projectMember = invite.getProjectMember();
        if (project.getProjectMember().contains(projectMember)) {
            throw new AlreadyProjectMemberException("이미 프로젝트의 멤버입니다.");
        }
        project.getProjectMember().add(projectMember);

        // 5. 저장
        inviteRepository.save(invite);
        projectRepository.save(project);
    }

    @Transactional
    @Override
    public void refuseInvite(UserDetails userDetails, AcceptInviteRequestDto dto) {
        // 1. 초대 정보 조회
        Invite invite = inviteRepository.findById(dto.getInviteId())
                .orElseThrow(() -> new InviteNotFoundException("초대가 존재하지 않습니다."));

        // 2. 초대 수락 자격 확인 (본인만 수락 가능)
        String username = userDetails.getUsername();
        if (!invite.getProjectMember().getUsername().equals(username)) {
            throw new NotInviteOwnerException("본인만 초대를 수락할 수 있습니다.");
        }

        inviteRepository.delete(invite);
    }

    @Override
    public List<Invite> getAllInvites() {
        return inviteRepository.findAll();
    }
}
