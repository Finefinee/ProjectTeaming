package Project.Teaming.Invite.Repository;

import Project.Teaming.Invite.Entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {
}
