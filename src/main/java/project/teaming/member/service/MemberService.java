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
        // 비밀번호 검증 먼저 수행
        String rawPassword = request.password();
        if (!isValidPassword(rawPassword)) {
            return ResponseEntity.badRequest().body(Map.of("error", "비밀번호는 최소 8자 이상이며, 영문자, 숫자, 특수문자를 포함해야 합니다."));
        }

            // 검증 통과 후 회원가입 절차
        Member member = Member.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(rawPassword))
                .email(request.email())
                .class_code(request.class_code())
                .role("ROLE_USER")
                .build();
        memberRepository.save(member);

        CreateTokenRequest tokenRequest = new CreateTokenRequest(
                member.getUsername(),
                member.getName(),
                member.getClass_code()
        );
        String token = jwtProvider.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
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
                member.getClass_code()
        );
        String token = jwtProvider.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // 비밀번호 검증식
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        return hasLetter && hasDigit && hasSpecial;
    }
}
