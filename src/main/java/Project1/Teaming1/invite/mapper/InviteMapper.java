package Project1.Teaming1.invite.mapper;

import Project1.Teaming1.invite.dto.InviteRequestDto;
import Project1.Teaming1.invite.dto.InviteResponseDto;
import Project1.Teaming1.invite.entity.Invite;
import Project1.Teaming1.member.entity.Member;
import Project1.Teaming1.project.entity.Project;

public class InviteMapper {

    // Invite → InviteResponseDto
    public static InviteResponseDto toDto(Invite invite) {
        return new InviteResponseDto(
                invite.getId(),
                invite.getProject().getProjectManager(),
                invite.getProjectMember().getUsername(),
                invite.isAccepted()
        );
    }
    
    // InviteRequestDto → Invite
    public static Invite toEntity(InviteRequestDto dto, Member projectMember, Project project) {
        return Invite.builder()
                .projectMember(projectMember)
                .project(project)
                .accepted(false) // 처음엔 초대 수락 안 했으니까 false로 초기화
                .build();
    }
}
