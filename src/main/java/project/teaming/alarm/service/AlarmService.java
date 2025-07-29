package project.teaming.alarm.service;

import org.springframework.security.core.userdetails.UserDetails;
import project.teaming.alarm.entity.Alarm;
import project.teaming.member.entity.Major;

import java.util.List;

public interface AlarmService {
    List<Alarm> findAllAlarm(UserDetails userDetails);
    void sendAlarm(Major major, Integer projectId);
    void readAlarm(UserDetails userDetails, Long alarmId);
}
