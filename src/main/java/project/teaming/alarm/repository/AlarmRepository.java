package project.teaming.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.teaming.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
