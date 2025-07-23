package Project1.Teaming1.project.service;

import Project1.Teaming1.member.entity.Member;
import Project1.Teaming1.member.repository.MemberRepository;
import Project1.Teaming1.project.entity.Project;
import Project1.Teaming1.project.repository.ProjectRepository;
import Project1.Teaming1.project.dto.CreateProjectRequest;
import Project1.Teaming1.project.dto.DeleteProjectRequest;
import Project1.Teaming1.project.dto.UpdateProject;
import Project1.Teaming1.project.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    //프로젝트 생성
    public ProjectResponse createProject(CreateProjectRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Member member = memberRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        List<Member> MemberList = new ArrayList<>();
        MemberList.add(member);
        Project project = Project.builder()
                .title(request.title())
                .content(request.content())
                .projectMember(MemberList)
                .projectManager(member.getUsername())
                .build();

        projectRepository.save(project);

        return new ProjectResponse(
              request.title(),
              request.content(),
                member.getUsername(),
                member.getUsername()
        );
    }
    public ResponseEntity<?> deleteProject(DeleteProjectRequest request) {
        Project project = projectRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다."));

        projectRepository.delete(project);
        return ResponseEntity.ok(Map.of("message", "프로젝트가 삭제되었습니다."));
    }

    public ProjectResponse  updateProject(UpdateProject request) {
//        Project e = projectRepository.findById(request.id()).orElse(null);
        Project project = projectRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

            project.setTitle(request.title());
            project.setContent(request.content());
            project.setProjectManager(request.projectManager());

            projectRepository.save(project);
            return ProjectResponse.of(project);
    }

    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream().map(ProjectResponse::of).collect(Collectors.toList());
    }
}