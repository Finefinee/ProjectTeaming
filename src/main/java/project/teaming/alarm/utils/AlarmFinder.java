package project.teaming.alarm.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.teaming.alarm.entity.Alarm;
import project.teaming.alarm.exception.AlarmNotFoundException;
import project.teaming.alarm.repository.AlarmRepository;

@Component
@RequiredArgsConstructor
public class AlarmFinder {

    private final AlarmRepository alarmRepository;

    public Alarm findAlarmByIdOrElseThrow(Long id) {
        return alarmRepository.findById(id)
                .orElseThrow(() -> new AlarmNotFoundException("알림이 없습니다."));
    }
}
