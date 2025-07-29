package project.teaming.alarm.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.teaming.alarm.entity.Alarm;
import project.teaming.alarm.exception.NotAlarmOwnerException;
import project.teaming.alarm.repository.AlarmRepository;
import project.teaming.alarm.service.AlarmService;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;
import project.teaming.project.service.ProjectService;

@RequiredArgsConstructor
@Component
public class AlarmValidator {

    private final AlarmRepository alarmRepository;
    private final ProjectService projectService;
    private final AlarmService alarmService;

    public Member validateAlarmOwner(UserDetails userDetails, Long alarmId) {

        Alarm alarm = alarmService.findAlarmByIdOrElseThrow(alarmId);

        if (!userDetails.getUsername().equals(alarm.getMember().getUsername())) {
            throw new NotAlarmOwnerException("이 알림은 본인의 알림이 아닙니다.");
        }

        return alarm.getMember();

    }
}
