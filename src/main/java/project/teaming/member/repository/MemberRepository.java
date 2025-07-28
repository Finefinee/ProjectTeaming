package project.teaming.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.teaming.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findById(int id);

    Optional<Member> findByUsername(String username);

    @Query("SELECT m.name FROM Member m")
    List<String> findAllNames();

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByClassCode(String classCode);
}
