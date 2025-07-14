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
        return ResponseEntity.ok(member);
    }

    // 로그인
    public String login (LoginRequest request) {
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
        return jwtProvider.createToken(tokenRequest);
    }

    public String delete(DeleteMemberRequest request) { //회원 탈퇴
        Member member = memberRepository.findById(request.id()).orElse(null);

        if (member == null) {
            throw new IllegalArgumentException(request.id() + "는 없는 사람입니다");
        }
        memberRepository.delete(member);
        return "성공적으로 탈퇴되었습니다.";
    }
    public MemberResponse findMemberById(int id) {
        Member e = memberRepository.findById(id).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException(id + "는 없는 사람입니다");
        }
        return MemberResponse.of(e);
    }
    public void deleteMemberById(int id) {
        Member e = memberRepository.findById(id).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException(id + "없는 사람입니다");
        }
        memberRepository.delete(e);
    }
    public MemberResponse updateMember(UpdateMember request) { // 프로필 수정
        Member e = memberRepository.findById(request.id()).orElse(null);
        if (e == null) {
            throw new IllegalArgumentException(request.id()+"는 없는 사람입니다.");
        }
        e.setId(request.id());

        memberRepository.save(e);

        return MemberResponse.of(e);
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream().map(MemberResponse::of).collect(Collectors.toList());
    }
}
