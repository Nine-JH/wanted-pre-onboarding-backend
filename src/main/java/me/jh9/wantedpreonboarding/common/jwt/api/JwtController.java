package me.jh9.wantedpreonboarding.common.jwt.api;

import jakarta.validation.Valid;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;
import me.jh9.wantedpreonboarding.common.jwt.api.request.RefreshAccessTokenRequest;
import me.jh9.wantedpreonboarding.common.jwt.application.response.RefreshResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/api/v1/auth/token")
    public ResponseEntity<RefreshResponse> refreshAccessToken(@Valid @RequestBody RefreshAccessTokenRequest request) {
        RefreshResponse response = jwtService.refreshAccessToken(
            RefreshAccessTokenServiceRequest.toServiceDto(request.refreshToken(),
                System.currentTimeMillis()));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }
}
