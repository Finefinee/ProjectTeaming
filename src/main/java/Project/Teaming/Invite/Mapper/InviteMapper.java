package Project.Teaming.Invite.Mapper;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Dto.InviteResponseDto;
import Project.Teaming.Invite.Entity.Invite;
import Project.Teaming.Member.Entity.Member;
import Project.Teaming.Project.Entity.Project;

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
