package project.teaming.alarm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.teaming.alarm.dto.AlarmGenerateRequestDto;
import project.teaming.alarm.entity.Alarm;
import project.teaming.alarm.exception.AlarmNotFoundException;
import project.teaming.alarm.repository.AlarmRepository;
import project.teaming.alarm.service.AlarmService;
import project.teaming.alarm.utils.AlarmGenerator;
import project.teaming.alarm.utils.AlarmValidator;
import project.teaming.member.entity.Major;
import project.teaming.member.entity.Member;
import project.teaming.member.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmServiceByMajor implements AlarmService {

    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmGenerator alarmGenerator;
    private final AlarmValidator  alarmValidator;

    @Override
    public void sendAlarm(Major major, Integer projectId) {
        List<Member> members = memberRepository.findAllByMainMajor(major.toString());

        for (Member member : members) {
            // AlarmGenerateRequestDto 생성
            AlarmGenerateRequestDto requestDto = new AlarmGenerateRequestDto(
                    member.getUsername(),
                    projectId
            );

            // AlarmGenerator를 사용해서 Alarm 생성
            Alarm alarm = alarmGenerator.generateAlarm(requestDto);

            // Alarm을 데이터베이스에 저장
            alarmRepository.save(alarm);
        }

    }

    @Override
    public void readAlarm(UserDetails userDetails, Long alarmId) {

        alarmValidator.validateAlarmOwner(userDetails, alarmId);

        Alarm alarm = findAlarmByIdOrElseThrow(alarmId);

        alarm.markAsRead();

    }

    @Override
    public Alarm findAlarmByIdOrElseThrow(Long id) {
        return alarmRepository.findById(id)
                .orElseThrow(() -> new AlarmNotFoundException("알림이 없습니다."));
    }
}
