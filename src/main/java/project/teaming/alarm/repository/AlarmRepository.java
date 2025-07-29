package project.teaming.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.teaming.alarm.entity.Alarm;
import project.teaming.member.entity.Member;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByMember(Member member);
}
