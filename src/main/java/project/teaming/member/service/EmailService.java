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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import project.teaming.config.RedisConfig;
import project.teaming.member.dto.BaseResponse;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	private final JavaMailSender mailSender;
	private final RedisConfig redisConfig;
	private int authNumber;

	// 이메일 인증에 필요한 정보
	@Value("${spring.mail.username}") private String serviceName;

	public void makeRandomNum() {
		authNumber = 100000 + new Random() .nextInt(899999); // 6자리 인증번호
	}

	public void sendEmail(String email) {
		makeRandomNum();
		String title = "Teaming 회원가입용 본인인증 코드 입니다.";
		String content = "이 메일은 수신용이며 회신하지 마십시오." +
				"<br><br>" +
				"인증번호는 " + authNumber + " 입니다." +
				"<br>" +
				"타인에게 해당 인증번호를 노출하지 마십시오.";

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
