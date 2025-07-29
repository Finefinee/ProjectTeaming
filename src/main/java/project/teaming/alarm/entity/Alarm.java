package project.teaming.alarm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 알림을 받는 회원

    @OneToOne(fetch = FetchType.LAZY) // (구인이 완료될 때까지 프로젝트 당 1개의 알림밖에 보낼 수 없음)
    private Project project; // 알림과 관련된 프로젝트
}
