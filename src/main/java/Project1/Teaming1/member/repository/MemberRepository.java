package Project1.Teaming1.member.repository;

import Project1.Teaming1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findById(int id);

    Optional<Member> findByUsername(String username);
}
