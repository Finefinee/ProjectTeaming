package project.teaming.alarm.dto;

public record AlarmResponseDto(
        String username,
        Long projectId,
        boolean isRead
) {
}
