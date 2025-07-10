package Project.Teaming.Project.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title; //제목
    @Column(length = 500)
    private String content; // 콘텐츠 설명
    @Column(nullable = false)
    private String projectManager; // 본명

    private String projectMember; // 배열
}