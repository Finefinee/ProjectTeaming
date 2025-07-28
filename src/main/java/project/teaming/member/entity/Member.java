package project.teaming.member.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@Table(name = "member")
@SuperBuilder
public class Member {

    @Id
    @Column(length = 25)
    private String username; // 사용자명(아이디)

    @Column(length = 4)
    private String name; // 실명

    @Column(length = 200) // 비밀번호 길이 100 -> 200으로 변경
    private String password; //비밀번호

    @Column(unique = true)
    private String email; // 이메일

    @Column(unique = true)
    private int grade; // 학번

    private String role; //권한
}