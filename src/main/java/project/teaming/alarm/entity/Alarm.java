package project.teaming.alarm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;

@Getter
@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isRead; // 읽음 여부

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 알림을 받는 회원

    @OneToOne(fetch = FetchType.LAZY) // (구인이 완료될 때까지 프로젝트 당 1개의 알림밖에 보낼 수 없음)
    private Project project; // 알림과 관련된 프로젝트

    public void markAsRead() {
        this.isRead = true; // 알림을 읽음으로 표시
    }
}
