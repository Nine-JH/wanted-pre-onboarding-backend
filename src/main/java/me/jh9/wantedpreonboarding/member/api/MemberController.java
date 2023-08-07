package me.jh9.wantedpreonboarding.member.api;

import jakarta.validation.Valid;
import me.jh9.wantedpreonboarding.member.api.request.LoginRequest;
import me.jh9.wantedpreonboarding.member.api.request.SignUpRequest;
import me.jh9.wantedpreonboarding.member.application.response.LoginResponse;
import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.application.usecase.LoginUseCase;
import me.jh9.wantedpreonboarding.member.application.usecase.SignUpUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member")
@RestController
public class MemberController {

    private final LoginUseCase loginUseCase;
    private final SignUpUseCase signUpUseCase;

    public MemberController(LoginUseCase loginUseCase, SignUpUseCase signUpUseCase) {
        this.loginUseCase = loginUseCase;
        this.signUpUseCase = signUpUseCase;
    }

    @PostMapping("/signUp")
    public ResponseEntity<MemberResponse> signUp(@RequestBody @Valid SignUpRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(signUpUseCase.signUp(request.toServiceRequest()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginUseCase
                .login(new LoginServiceRequest(request.email(), request.password())));
    }
}
