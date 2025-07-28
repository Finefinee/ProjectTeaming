package project.teaming.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.teaming.member.dto.CreateTokenRequest;
import project.teaming.member.dto.LoginRequest;
import project.teaming.member.dto.SignUpRequest;
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
    public ResponseEntity<?> signUp (SignUpRequest request) {

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
        // 학번 유효성 체크
        String rawClassCode = request.classCode();
        if (!isValidClassCode(rawClassCode)) {
            return ResponseEntity.badRequest().body(Map.of("classCode_error", "잘못된 학번입니다."));
        }
        // 학번 중복 체크
        if (memberRepository.existsByClassCode(request.classCode())) {
            return ResponseEntity.badRequest().body(Map.of("classcode_error", "이미 사용중인 학번입니다."));
        }



        // 검증 통과 후 회원가입 절차
        Member member = Member.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(rawPassword))
                .email(request.email())
                .classCode(request.classCode())
                .role("ROLE_USER") // 일단 일반 유저들 가입할 땐 무조건 USER로, 나중에 관리자 계정만들때는 ROLE_ADMIN으로 고쳐서 잠깐 하면 됨
                .build();
        memberRepository.save(member);

        CreateTokenRequest tokenRequest = new CreateTokenRequest(
                member.getUsername(),
                member.getName(),
                member.getClassCode()
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
                member.getClassCode()
        );
        String token = jwtProvider.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

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

    // 학번 검증식
    private boolean isValidClassCode(String classCode) {
        try {
            int code = Integer.parseInt(classCode);

            // 존재할 수 있는 최소 학번, 최대 학번
            if (code < 1101 || code > 3429) {
                return false;
            }

            int th = code / 1000;
            int hu = (code / 100) % 10;
            int ten = (code / 10) % 10;

            // 학년(1, 2, 3 학년)
//            if (th < 1 || th > 3) {
//                return false;
//            }

            // 반(1, 2, 3, 4반)
            if (hu < 1 || hu > 4) {
                return false;
            }

            //번호(최소 0, 최대 2)
            // 01번 부터 최대 29번 까지
            if (ten > 2) {
                return false;
            }

            // 모든 조건을 통과한 경우 true
            return true;

        } // 만약 숫자가 아닌 경우 예외 출력
        catch (NumberFormatException e) {
            return false;
        }
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("사용자 없음"));
    }
}