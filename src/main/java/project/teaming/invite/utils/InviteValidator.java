package project.teaming.invite.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.teaming.invite.entity.Invite;
import project.teaming.invite.exception.NotInviteOwnerException;
import project.teaming.member.entity.Member;
import project.teaming.member.exception.MemberNotFoundException;
import project.teaming.member.repository.MemberRepository;
import project.teaming.project.entity.Project;
import project.teaming.project.exception.ProjectNotFoundException;
import project.teaming.project.repository.ProjectRepository;

@Component
@RequiredArgsConstructor
public class InviteValidator {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    public void validateInviteAuthority(String username, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("프로젝트 없음"));

        if (!project.getProjectManager().equals(username)) {
            throw new NotInviteOwnerException("프로젝트 팀장만 초대 가능");
        }
    }

    public void validateInvitee(Invite invite, String username) {
        if (!invite.getProjectMember().getUsername().equals(username)) {
            throw new NotInviteOwnerException("본인만 초대를 수락하거나 거절할 수 있습니다.");
        }
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("사용자 없음"));
    }

    public Project findProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("프로젝트 없음"));
    }
}
