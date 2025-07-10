package Project.Teaming.Invite.Mapper;

import Project.Teaming.Invite.Dto.InviteRequestDto;
import Project.Teaming.Invite.Dto.InviteResponseDto;
import Project.Teaming.Invite.Entity.Invite;

public class InviteMapper {
    private InviteResponseDto toResponseDto(Invite invite) {
        InviteResponseDto dto = new InviteResponseDto();
        dto.setId(invite.getId());
        dto.setProjectManagerUsername(invite.getProjectManager().getUsername());
        dto.setProjectMemberUsername(invite.getProjectMember().getUsername());
        dto.setAccepted(invite.isAccepted());
        return dto;
    }

    private InviteRequestDto toRequestDto(Invite invite) {
        InviteRequestDto dto = new InviteRequestDto();
        dto.setProjectMemberUsername(invite.getProjectMember().getUsername());
        return dto;
    }
}
