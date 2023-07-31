package me.jh9.wantedpreonboarding.member.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;

public record LoginRequest(
    @NotBlank(message = "이메일을 입력해주세요") @Email(message = "아이디는 이메일 형식어야 합니다.") String email,
    @NotBlank(message = "비밀번호를 입력해주세요") @Size(min = 8, message = "비밀번호는 8자리 이상이어야 합니다.") String password
) {

    public LoginServiceRequest toServiceRequest() {
        return new LoginServiceRequest(email, password);
    }
}
