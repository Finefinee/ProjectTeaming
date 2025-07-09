package Project.Teaming.Member.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 25)
    private String username; //Id
    @Column(length = 4)
    private String name; //본명/실명
    @Column(length = 16)
    private String password; //비번
    private int userId; // 학번

    private String role; //권한
}