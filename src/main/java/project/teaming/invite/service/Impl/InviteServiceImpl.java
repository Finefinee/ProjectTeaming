package project.teaming.invite.service.Impl;

import project.teaming.invite.dto.AcceptInviteRequestDto;
import project.teaming.invite.dto.InviteRequestDto;
import project.teaming.invite.entity.Invite;
import project.teaming.invite.exception.AlreadyProjectMemberException;
import project.teaming.invite.exception.InviteNotFoundException;
import project.teaming.invite.exception.NotInviteOwnerException;
import project.teaming.invite.repository.InviteRepository;
import project.teaming.invite.service.InviteService;
import project.teaming.invite.utils.InviteGenerator;
import project.teaming.invite.utils.InviteValidator;
import project.teaming.member.entity.Member;
import project.teaming.member.exception.MemberNotFoundException;
import project.teaming.member.repository.MemberRepository;
import project.teaming.project.entity.Project;
import project.teaming.project.exception.ProjectNotFoundException;
import project.teaming.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final InviteGenerator inviteGenerator;
    private final InviteValidator inviteValidator;

    @Override
    @Transactional
    public void sendInvite(UserDetails userDetails, InviteRequestDto inviteRequestDto) {
        // 1. 프로젝트 팀장(로그인 유저) 조회
        Member projectManager = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new MemberNotFoundException("프로젝트 팀장 없음"));

        if (!Objects.equals(userDetails.getUsername(), projectRepository.findById(inviteRequestDto.projectId())
                .orElseThrow(() -> new ProjectNotFoundException("프로젝트 없음"))
                .getProjectManager())
        ) {
            throw new NotInviteOwnerException("프로젝트 팀장만 초대할 수 있습니다.");
        }

        // 2. 초대받을 멤버 조회
        Member projectMember = memberRepository.findByUsername(inviteRequestDto.projectMemberUsername())
                .orElseThrow(() -> new MemberNotFoundException("프로젝트 멤버(초대 대상) 없음"));

        // 3. 프로젝트 조회
        Project project = projectRepository.findById(inviteRequestDto.projectId())
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
        Invite invite = inviteRepository.findById(dto.inviteId())
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
        Invite invite = inviteRepository.findById(dto.inviteId())
                .orElseThrow(() -> new InviteNotFoundException("초대가 존재하지 않습니다."));

        // 2. 초대 수락 자격 확인 (본인만 수락 가능)
        String username = userDetails.getUsername();
        if (!invite.getProjectMember().getUsername().equals(username)) {
            throw new NotInviteOwnerException("본인만 초대를 수락할 수 있습니다.");
        }

        inviteRepository.delete(invite);
    }

    @Override
    public List<Invite> getAllInvitesByUsername(UserDetails userDetails) {
        return inviteRepository.findAll().stream()
            .filter(invite -> invite.getProjectMember().getUsername().equals(userDetails.getUsername()))
            .collect(Collectors.toList());
    }
}
