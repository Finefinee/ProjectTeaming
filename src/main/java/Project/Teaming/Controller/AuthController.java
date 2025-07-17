//package Project.Teaming.Controller;
//
//import Project.Teaming.JWT.JwtProvider;
//import Project.Teaming.Member.Interface.MemberRepository;
//import Project.Teaming.Member.Service.MemberService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import Project.Teaming.DTO.*;
//
//// @CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    private final JwtProvider jwtprovider;
//    private final PasswordEncoder passwordEncoder;
//    private final MemberService memberService;
//
//
//    public AuthController(JwtProvider provider, PasswordEncoder passwordEncoder, MemberRepository memberRepository, MemberService memberService) {
//        this.jwtprovider = provider;
//        this.passwordEncoder = passwordEncoder;
//        this.memberService = memberService;
//    }
//
//    @PostMapping("/signup")
//    public void signup(@RequestBody SignUpRequest request) {
//        memberService.signUp(request);
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest request) {
//        return memberService.login(request);
//    }
//}
