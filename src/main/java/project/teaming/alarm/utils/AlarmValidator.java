package project.teaming.alarm.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.teaming.alarm.entity.Alarm;
import project.teaming.alarm.exception.NotAlarmOwnerException;
import project.teaming.member.entity.Member;

@Component
@RequiredArgsConstructor
public class AlarmValidator {

    private final AlarmFinder alarmFinder;

    public Member validateAlarmOwner(UserDetails userDetails, Long alarmId) {

        Alarm alarm = alarmFinder.findAlarmByIdOrElseThrow(alarmId);

        if (!userDetails.getUsername().equals(alarm.getMember().getUsername())) {
            throw new NotAlarmOwnerException("이 알림은 본인의 알림이 아닙니다.");
        }

        return alarm.getMember();

    }
}
