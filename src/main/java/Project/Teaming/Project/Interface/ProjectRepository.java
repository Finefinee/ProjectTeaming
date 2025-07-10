package Project.Teaming.Project.Interface;

import Project.Teaming.Project.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
