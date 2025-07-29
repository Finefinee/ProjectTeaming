package project.teaming.alarm.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.teaming.alarm.dto.AlarmGenerateRequestDto;
import project.teaming.alarm.entity.Alarm;
import project.teaming.member.service.MemberService;
import project.teaming.project.service.ProjectService;

@Component
@RequiredArgsConstructor
public class AlarmGenerator {

    private final ProjectService projectService;
    private final MemberService memberService;

    public Alarm generateAlarm(AlarmGenerateRequestDto alarmGenerateRequestDto) {
        return Alarm.builder()
                .member(memberService.findMemberByUsernameOrElseThrow(alarmGenerateRequestDto.username()))
                .project(projectService.findProjectByIdOrElseThrow(alarmGenerateRequestDto.projectId()))
                .build();
    }
}
