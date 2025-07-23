package Project.Teaming.member.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(unique = true, length = 25)
    private String username; // 사용자명(아이디)

//    @Column(length = 4)
    private String name; // 실명

//    @Column(length = 16)
    private String password; //비밀번호

    private String email; // 이메일

    private int class_code; // 학번

    private String role; //권한
}