package project.teaming.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.teaming.config.RedisConfig;
import project.teaming.member.dto.BaseResponse;
import project.teaming.member.dto.CreateTokenRequest;
import project.teaming.member.dto.LoginRequest;
import project.teaming.member.dto.SignUpRequest;
import project.teaming.member.entity.Major;
import project.teaming.member.entity.Member;
import project.teaming.member.exception.MemberNotFoundException;
import project.teaming.member.jwt.JwtProvider;
import project.teaming.member.repository.MemberRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 이메일 인증
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    private final JavaMailSender mailSender;
    private final RedisConfig redisConfig;
    private int authNumber;

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
            return ResponseEntity.badRequest().body(Map.of("email_error", "해당 이메일로 이미 가입된 계정이 있습니다."));
        }
        // 학년 유효성 체크
        int rawGrade = request.grade();
        if (!isValidGrade(rawGrade)) {
            return ResponseEntity.badRequest().body(Map.of("classCode_error", "잘못된 학년입니다."));
        }

        // 만약 부전공이 없다면, Null 대신에 None이 들어감
        Major subMajor1 = request.subMajor();
        if (subMajor1 == null) {
            subMajor1 = Major.NONE;
        }


        // 검증 통과 후 회원가입
        Member member = Member.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(rawPassword))
                .email(request.email())
                .grade(request.grade())
                .mainMajor(request.mainMajor())
                .subMajor(subMajor1)
                .role("ROLE_USER") // 일단 일반 유저들 가입할 땐 무조건 USER로, 나중에 관리자 계정만들때는 ROLE_ADMIN으로 고쳐서 하면 됨
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

    /** 이메일 인증 관련 **/
    // 이메일 인증에 필요한 정보
    @Value("${spring.mail.username}") private String serviceName;

    public void makeRandomNum() {
        authNumber = 100000 + new Random() .nextInt(899999); // 6자리 인증번호
    }

    public void sendEmail(String email) {
        makeRandomNum();
        String title = "Teaming 회원가입용 본인인증 코드 입니다.";
        String content = """
        <div style="font-family: 'Arial', sans-serif; max-width: 600px; margin: auto; padding: 20px; background-color: #ffffff;">
            <h2 style="font-weight: 700; font-size: 20px; margin-bottom: 4px;">
                프로젝트를 <span style="color: #3E80F4;">더</span> 쉽게, 협업을 <span style="color: #3E80F4;">더</span> 쉽게!!
            </h2>
            <p style="font-weight: 700; margin-top: 0; font-size: 16px;">
                저희 <span style="color: #3E80F4;">Teaming</span> 서비스를 이용해 주셔서 감사합니다.
            </p>

            <p style="font-weight: 700; font-size: 16px; margin-top: 24px;">
                아래 인증코드를 회원가입 란에 정확히 입력해주세요.
            </p>
            <p style="color: #555; font-size: 12px; margin-top: 4px;">
                코드는 5분동안 유효합니다.
            </p>

            <div style="margin-top: 24px; text-align: center;">
                <div style="display: inline-block; background-color: #3E80F4; padding: 16px 40px; border-radius: 50px;">
                    <span style="color: #ffffff; font-size: 28px; font-weight: bold; letter-spacing: 2px;">
                        %s
                    </span>
                </div>
            </div>
        </div>
        """.formatted(authNumber);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(serviceName); // 서비스 이름
            helper.setTo(email); // 사용자 이메일
            helper.setSubject(title); // 이메일 제목
            helper.setText(content, true); // content, HTML: true
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("인증코드를 정상적으로 발송하지 못했습니다.", e);
        }

        // 5분 동안 redis에 이메일과 인증 코드 저장
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        valueOperations.set(email, Integer.toString(authNumber), 5, TimeUnit.MINUTES);
    }

    // 인증 코드 확인
    public BaseResponse checkAuthNum(String email, String authNum) {
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        String code = valueOperations.get(email);

        if (Objects.equals(code, authNum)) {
            return BaseResponse.ok("이메일이 확인되었습니다.");
        } else {
            return BaseResponse.of(HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다. 다시 시도해주시길 바랍니다.");
        }
    }
}