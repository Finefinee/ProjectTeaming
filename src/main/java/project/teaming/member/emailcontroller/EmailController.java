package project.teaming.member.emailcontroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.teaming.member.dto.BaseResponse;
import project.teaming.member.dto.EmailCheckRequest;
import project.teaming.member.dto.EmailSendRequest;
import project.teaming.member.service.EmailService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "이메일 인증", description = "이메일 발송 및 인증 API")
public class EmailController {
	private final EmailService emailService;

	@PostMapping("/send")
	@Operation(summary = "인증코드 발송")
	public BaseResponse sendEmail(@RequestBody EmailSendRequest request) {
		emailService.sendEmail(request.email());
		return BaseResponse.ok("이메일 전송에 성공했습니다.");
	}

	@PostMapping("/check")
	@Operation(summary = "이메일 확인")
	public BaseResponse checkEmail(@RequestBody EmailCheckRequest request) {
		return emailService.checkAuthNum(request.email(), request.authNum());
	}
}
