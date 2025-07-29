package project.teaming.alarm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.teaming.alarm.dto.AlarmGenerateRequestDto;
import project.teaming.alarm.service.AlarmService;
import project.teaming.member.entity.Major;
import project.teaming.member.entity.Member;
import project.teaming.member.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmServiceByMajor implements AlarmService {

    private final MemberRepository memberRepository;

    @Override
    public void sendAlarm(Major major, Integer projectId) {
        List<Member> members = memberRepository.findAllByMainMajor(major.toString());

        for (Member member : members) {

        }

    }

    @Override
    public void readAlarm(UserDetails userDetails, AlarmGenerateRequestDto alarmRequestDto) {

    }
}
