package project.teaming.member.service;

import project.teaming.member.dto.CreateTokenRequest;
import project.teaming.member.dto.LoginRequest;
import project.teaming.member.dto.SignUpRequest;
import project.teaming.member.jwt.JwtProvider;
import project.teaming.member.entity.Member;
import project.teaming.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


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
}
