package Project.Teaming.Project.Interface;

import Project.Teaming.Project.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
