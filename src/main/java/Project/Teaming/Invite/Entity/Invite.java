package Project.Teaming.Invite.Entity;

import Project.Teaming.Member.Member;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Invites")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "manager", nullable = false)
    @ManyToOne
    private Member projectManager;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne
    private Member projectMember;

    @Column(nullable = false)
    private boolean accepted = false;
}
