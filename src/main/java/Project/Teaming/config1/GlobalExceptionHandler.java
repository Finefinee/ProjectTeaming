package Project.Teaming.config1;

import Project.Teaming.invite.Exception.AlreadyProjectMemberException;
import Project.Teaming.invite.Exception.InviteNotFoundException;
import Project.Teaming.invite.Exception.NotInviteOwnerException;
import Project.Teaming.member.exception.MemberNotFoundException;
import Project.Teaming.project.exception.ProjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFound(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InviteNotFoundException.class)
    public ResponseEntity<String> handleInviteNotFound(InviteNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFound(ProjectNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotInviteOwnerException.class)
    public ResponseEntity<String> handleNotInviteOwnerException(NotInviteOwnerException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(AlreadyProjectMemberException.class)
    public ResponseEntity<String> handleAlreadyProjectMemberException(AlreadyProjectMemberException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("요청 처리 중 에러 발생: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}