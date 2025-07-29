package project.teaming.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.teaming.member.dto.CreateTokenRequest;
import project.teaming.member.dto.LoginRequest;
import project.teaming.member.dto.SignUpRequest;
import project.teaming.member.entity.Major;
import project.teaming.member.entity.Member;
import project.teaming.member.exception.MemberNotFoundException;
import project.teaming.member.jwt.JwtProvider;
import project.teaming.member.repository.MemberRepository;

import java.util.Map;


@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 멤버 생성(비밀번호 암호화 포함)
    public ResponseEntity<?> signUp(SignUpRequest request) {

        // 상황에 따른 예외 출력
        // 비밀번호 검증 먼저 수행
        String rawPassword = request.password();
        if (!isValidPassword(rawPassword)) {
            return ResponseEntity.badRequest().body(Map.of("password_error", "비밀번호는 최소 8자 이상이며, 영문자, 숫자, 특수문자를 포함해야 합니다."));
        }
        // 사용자명 중복 체크
        if (memberRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body(Map.of("username_error", "중복된 사용자명 입니다."));
        }
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body(Map.of("email_error", "해당 이메일로 가입된 계정이 있습니다."));
        }
        // 학년 유효성 체크
        int rawGrade = request.grade();
        if (!isValidGrade(rawGrade)) {
            return ResponseEntity.badRequest().body(Map.of("classCode_error", "잘못된 학년입니다."));
        }

        Major subMajor1 = request.subMajor();
        if (subMajor1 == null) {
            subMajor1 = Major.NONE;
        }


        // 검증 통과 후 회원가입 절차
        Member member = Member.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(rawPassword))
                .email(request.email())
                .grade(request.grade())
                .mainMajor(request.mainMajor())
                .subMajor(subMajor1)
                .role("ROLE_USER") // 일단 일반 유저들 가입할 땐 무조건 USER로, 나중에 관리자 계정만들때는 ROLE_ADMIN으로 고쳐서 잠깐 하면 됨
                .build();
        memberRepository.save(member);

        CreateTokenRequest tokenRequest = new CreateTokenRequest(
                member.getUsername(),
                member.getName(),
                member.getGrade()
        );
        String token = jwtProvider.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // 로그인
    public ResponseEntity<?> login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.username()).orElseThrow(()
                -> new IllegalArgumentException("사용자명 혹은 비밀번호가 잘못되었습니다."));
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new IllegalArgumentException("사용자명 혹은 비밀번호가 잘못되었습니다.");
        }

        CreateTokenRequest tokenRequest = new CreateTokenRequest(
                member.getUsername(),
                member.getName(),
                member.getGrade()
        );
        String token = jwtProvider.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * 유효성 검증을 위한 여러 검증식들
     **/
    // 비밀번호 검증식
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) { // 만약  비밀번호가 공백이거나 8자 미만인지
            return false;
        }
        boolean hasLetter = password.matches(".*[a-zA-Z].*"); // 영어 대소문자중 하나라도 포함되어 있는지
        boolean hasDigit = password.matches(".*\\d.*"); // 숫자가 하나라도 있는지
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); // 특수문자가 하나라도 있는지
        return hasLetter && hasDigit && hasSpecial;
    }

    // 학년 유효성 검사
    private boolean isValidGrade(int grade) {
        return grade <= 4 && grade >= 0;
    }

    public Member findMemberByUsernameOrElseThrow(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("사용자 없음"));
    }
}