package Project.Teaming.Member.Controller;

import Project.Teaming.DTO.LoginRequest;
import Project.Teaming.DTO.SignUpRequest;
import Project.Teaming.Member.Request.CreateMemberRequest;
import Project.Teaming.Member.Request.UpdateMember;
import Project.Teaming.Member.Response.MemberResponse;
import Project.Teaming.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public void createMember(@RequestBody SignUpRequest request) {
        memberService.signUp(request);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
        memberService.login(request);
    }

    @GetMapping("/{id}")
    public MemberResponse findMemberById(@PathVariable int id) {
        return memberService.findMemberById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMemberById(@PathVariable int id) {
        memberService.deleteMemberById(id);
    }

    @PatchMapping()
    public MemberResponse updateMember (@RequestBody UpdateMember request) {
        return memberService.updateMember(request);
    }

    @GetMapping()
    public List<MemberResponse> findAll() {
        return memberService.findAll();
    }
}
