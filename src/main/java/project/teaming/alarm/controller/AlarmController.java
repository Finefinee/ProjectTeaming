package project.teaming.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.teaming.alarm.entity.Alarm;
import project.teaming.alarm.service.AlarmService;

import java.util.List;

@RestController
@RequestMapping("/alarm")
@CrossOrigin(origins = "*")
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(
            @Qualifier("alarmServiceByAllMajor") AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    // 자기꺼 보기
    @GetMapping
    ResponseEntity<List<Alarm>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(alarmService.findAllAlarm(userDetails));
    }

    // 알림 보내기는 다른 곳에서

    // 알림 읽음
    @DeleteMapping("/{alarmId}")
    ResponseEntity<Void> readAlarm(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long alarmId) {
        alarmService.readAlarm(userDetails, alarmId);
        return ResponseEntity.noContent().build();
    }

    // 알림 전체 읽음
    @DeleteMapping
    ResponseEntity<Void> readAllAlarm(@AuthenticationPrincipal UserDetails userDetails) {
        List<Alarm> alarms = alarmService.findAllAlarm(userDetails);
        for (Alarm alarm : alarms) {
            alarmService.readAlarm(userDetails, alarm.getId());
        }
        return ResponseEntity.noContent().build();
    }
}
