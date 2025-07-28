package project.teaming.invite.entity;

import lombok.*;
import project.teaming.member.entity.Member;
import project.teaming.project.entity.Project;
import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "Invites")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "manager", nullable = false)
    @ManyToOne
    private Member projectManager;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne
    private Member projectMember;

    @JoinColumn(name = "project", nullable = false) // 어떤 프로젝트 초대인지
    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private boolean accepted = false;
}
