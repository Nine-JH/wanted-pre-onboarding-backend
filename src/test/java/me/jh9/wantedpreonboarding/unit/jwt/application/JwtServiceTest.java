package me.jh9.wantedpreonboarding.unit.jwt.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Date;
import java.util.Optional;
import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtEntity;
import me.jh9.wantedpreonboarding.common.jwt.infra.exception.JwtDeniedException;
import me.jh9.wantedpreonboarding.common.jwt.infra.exception.JwtExpiredException;
import me.jh9.wantedpreonboarding.utils.JwtTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JwtServiceTest extends JwtTestSupport {



    @DisplayName("createAccessToken(String subject, long currentTime) 은")
    @Nested
    class Context_CreateAccessToken {

        @DisplayName("생성에 성공한다.")
        @Test
        void _willSuccess() {
            // given
            String memberId = "1000";

            // when
            String result = jwtService.createAccessToken(memberId, System.currentTimeMillis());
            Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(result.substring(BEARER_TOKEN_PREFIX.length())).getBody();

            // then
            Assertions.assertThat(result).isNotNull().isNotBlank();
            Assertions.assertThat(claims.getSubject()).isEqualTo(memberId);
        }
    }

    @DisplayName("refreshAccessToken(RefreshAccessTokenServiceRequest serviceRequest) 은")
    @Nested
    class Context_RefreshAccessToken {

        @DisplayName("생성에 성공한다.")
        @Test
        void _willSuccess() {
            // given
            String memberId = "1000";

            // when
            String result = jwtService.createAccessToken(memberId, System.currentTimeMillis());
            Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(result.substring(BEARER_TOKEN_PREFIX.length())).getBody();

            // then
            Assertions.assertThat(result).isNotNull().isNotBlank();
            Assertions.assertThat(claims.getSubject()).isEqualTo(memberId);
        }

        @DisplayName("refreshToken이 존재하지 않으면 실패한다.")
        @Test
        void refreshTokenNotExist_willFail() {
            // given
            given(jwtRepository.findByJwtEntity(any(JwtEntity.class)))
                .willReturn(Optional.empty());

            RefreshAccessTokenServiceRequest request = new RefreshAccessTokenServiceRequest(
                "refreshToken", System.currentTimeMillis());

            // when then
            Assertions.assertThatThrownBy(() -> jwtService.refreshAccessToken(request))
                .isInstanceOf(JwtDeniedException.class);
        }

        @DisplayName("refreshToken 역시 만료되어있다면 실패한다.")
        @Test
        void refreshTokenExpiredToo_willFail() {
            // given
            String memberId = "1000";
            String expiredRefreshToken = Jwts.builder()
                .setSubject(memberId)
                .setExpiration(new Date(10))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

            given(jwtRepository.findByJwtEntity(any(JwtEntity.class)))
                .willReturn(Optional.of(expiredRefreshToken));

            RefreshAccessTokenServiceRequest request = new RefreshAccessTokenServiceRequest(
                expiredRefreshToken, System.currentTimeMillis());

            // when then
            Assertions.assertThatThrownBy(() -> jwtService.refreshAccessToken(request))
                .isInstanceOf(JwtExpiredException.class)
                .hasMessage("Jwt is Expired. Please Re-Login.");
        }

        @DisplayName("refreshToken가 유효하지 않으면 실패한다.")
        @Test
        void notOurRefreshToken_willFail() {
            // given
            String memberId = "1000";
            String expiredRefreshToken = Jwts.builder()
                .setSubject(memberId)
                .setExpiration(new Date(10))
                .signWith(SignatureAlgorithm.HS512, secretKey + "asdfg")
                .compact();

            RefreshAccessTokenServiceRequest request = new RefreshAccessTokenServiceRequest(
                expiredRefreshToken, System.currentTimeMillis());

            // when then
            Assertions.assertThatThrownBy(() -> jwtService.refreshAccessToken(request))
                .isInstanceOf(JwtDeniedException.class)
                .hasMessage("It is not signed from our server.");
        }
    }

    @DisplayName("createRefreshToken(String subject, long currentTime) 은")
    @Nested
    class Context_CreateRefreshToken {

        @DisplayName("생성에 성공한다.")
        @Test
        void _willSuccess() {
            // given
            String memberId = "1000";

            // when
            String result = jwtService.createRefreshToken(memberId, System.currentTimeMillis());
            Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(result.substring(BEARER_TOKEN_PREFIX.length())).getBody();

            // then
            Assertions.assertThat(result).isNotNull().isNotBlank();
            Assertions.assertThat(claims.getSubject()).isEqualTo(memberId);
        }
    }

    @DisplayName("verify(String token) 은")
    @Nested
    class Context_Verify {

        @DisplayName("올바른 토큰일 시 검증에 성공한다.")
        @Test
        void _willSuccess() {
            // given
            String memberId = "1000";
            String createdJwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(memberId).compact();
            // when
            Claims verifyResult = jwtService.verify("Bearer " + createdJwt);

            // then
            Assertions.assertThat(verifyResult).isNotNull();
            Assertions.assertThat(verifyResult.getSubject()).isEqualTo(memberId);
        }

        @DisplayName("만료시간이 지났다면 JwtExpiredException을 던진다.")
        @Test
        void tokenExpired_willFail() {
            // given
            String memberId = "1000";
            String createdJwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(memberId)
                .setExpiration(new Date(System.currentTimeMillis()))
                .compact();

            // when then
            Assertions.assertThatThrownBy(() -> jwtService.verify("Bearer " + createdJwt))
                .isInstanceOf(JwtExpiredException.class);
        }
    }
}
