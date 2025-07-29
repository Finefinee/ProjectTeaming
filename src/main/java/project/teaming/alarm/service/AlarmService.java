package project.teaming.alarm.service;

import org.springframework.security.core.userdetails.UserDetails;
import project.teaming.alarm.dto.AlarmGenerateRequestDto;
import project.teaming.member.entity.Major;

public interface AlarmService {
    void sendAlarm(Major major, Integer projectId);
    void readAlarm(UserDetails userDetails, AlarmGenerateRequestDto alarmRequestDto);
}
