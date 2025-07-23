package Project.Teaming.invite.Entity;

import Project.Teaming.member.entity.Member;
import Project.Teaming.project.entity.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
