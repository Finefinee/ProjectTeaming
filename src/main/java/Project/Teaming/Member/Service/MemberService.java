package Project.Teaming.Member.Service;

import Project.Teaming.DTO.CreateTokenRequest;
import Project.Teaming.DTO.LoginRequest;
import Project.Teaming.DTO.SignUpRequest;
import Project.Teaming.JWT.JwtProvider;
import Project.Teaming.Member.Entity.Member;
import Project.Teaming.Member.Interface.MemberRepository;
import Project.Teaming.Member.Request.DeleteMemberRequest;
import Project.Teaming.Member.Request.UpdateMember;
import Project.Teaming.Member.Response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 멤버 생성(비밀번호 암호화 포함)
    public ResponseEntity<?> signUp (SignUpRequest request) {
        Member member = Member.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .class_code(request.class_code())
                .role("ROLE_USER")
                .build();
        memberRepository.save(member);
        return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
    }

    // 로그인
    public ResponseEntity<?> login (LoginRequest request) {
        Member member = memberRepository.findByUsername(request.username()).orElseThrow(()
                -> new IllegalArgumentException("사용자명 혹은 비밀번호가 잘못되었습니다."));
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("사용자명 혹은 비밀번호가 잘못되었습니다.");
        }
        CreateTokenRequest tokenRequest = new CreateTokenRequest(
                member.getUsername(),
                member.getName(),
                member.getEmail(),
                member.getClass_code()
        );
        String token = jwtProvider.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    public ResponseEntity<?> delete(DeleteMemberRequest request) { //회원 탈퇴
        Member member = memberRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("선택하신 회원을 찾을 수 없습니다."));

        memberRepository.delete(member);
        return ResponseEntity.ok(Map.of("message", "회원탈퇴 되었습니다."));
    }



    public MemberResponse findMemberById(int id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->  new IllegalArgumentException(id+"는 없는 사람입니다."));

        return MemberResponse.of(member);
    }


    public void deleteMemberById(int id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "없는 사람입니다."));

        memberRepository.delete(member);
    }


    public MemberResponse updateMember(UpdateMember request) { // 프로필 수정
        Member member = memberRepository.findById(request.id())
                .orElseThrow( () -> new IllegalArgumentException(request.id()+"는 없는 사람입니다."));

        member.setName(request.name());
        member.setUsername(request.username());
        member.setEmail(request.Email());


        memberRepository.save(member);

        return MemberResponse.of(member);
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream().map(MemberResponse::of).collect(Collectors.toList());
    }
}
