package project.teaming.member.repository;

import project.teaming.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findById(int id);

    Optional<Member> findByUsername(String username);
}
