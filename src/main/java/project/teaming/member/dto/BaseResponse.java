package project.teaming.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

/** 이메일 보내는거와 관련있음 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private int status;

	public static <T> BaseResponse<T> ok(T data) {
		return BaseResponse.<T>builder()
				.success(true)
				.message("요청 성공")
				.data(data)
				.status(HttpStatus.OK.value())
				.build();
	}

	// 성공 응답
	public static BaseResponse<Void> ok(String message) {
		return BaseResponse.<Void>builder()
				.success(true)
				.message(message)
				.status(HttpStatus.OK.value())
				.build();
	}

	// 실패 응답
	public static <T> BaseResponse<T> of(HttpStatus status, String message) {
		return BaseResponse.<T>builder()
				.success(false)
				.message(message)
				.status(status.value())
				.build();
	}
}
