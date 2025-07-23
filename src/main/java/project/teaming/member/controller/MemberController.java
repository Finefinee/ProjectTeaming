package project.teaming.member.controller;

import project.teaming.member.dto.LoginRequest;
import project.teaming.member.dto.SignUpRequest;
import project.teaming.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody SignUpRequest request) {
        return memberService.signUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return memberService.login(request);
    }

//    @GetMapping("/{id}")
//    public MemberResponse findMemberById(@PathVariable int id) {
//        return memberService.findMemberById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteMemberById(@PathVariable int id) {
//        memberService.deleteMemberById(id);
//    }
//
//    @PatchMapping()
//    public MemberResponse updateMember (@RequestBody UpdateMember request) {
//        return memberService.updateMember(request);
//    }
//
//    @GetMapping()
//    public List<MemberResponse> findAll() {
//        return memberService.findAll();
//    }
}
