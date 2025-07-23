package Project1.Teaming1.project.repository;

import Project1.Teaming1.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
