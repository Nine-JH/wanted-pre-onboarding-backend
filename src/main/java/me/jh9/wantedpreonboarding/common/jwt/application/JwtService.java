package me.jh9.wantedpreonboarding.common.jwt.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import me.jh9.wantedpreonboarding.common.jwt.application.request.RefreshAccessTokenServiceRequest;
import me.jh9.wantedpreonboarding.common.jwt.application.response.RefreshResponse;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.AccessTokenUseCase;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.RefreshTokenUseCase;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.VerifyUseCase;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtEntity;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtRepository;
import me.jh9.wantedpreonboarding.common.jwt.domain.JwtType;
import me.jh9.wantedpreonboarding.common.jwt.infra.exception.JwtDeniedException;
import me.jh9.wantedpreonboarding.common.jwt.infra.exception.JwtExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService implements AccessTokenUseCase, RefreshTokenUseCase, VerifyUseCase {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final JwtRepository<JwtEntity> jwtRepository;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expired-time.access}")
    private String ACCESS_EXPIRED_TIME;

    @Value("${jwt.expired-time.refresh}")
    private String REFRESH_EXPIRED_TIME;

    public JwtService(JwtRepository<JwtEntity> jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    @Override
    public String createAccessToken(String subject, long currentTime) {
        long expiredTime = Long.parseLong(REFRESH_EXPIRED_TIME);

        return BEARER_TOKEN_PREFIX + createToken(subject, calcExpiredTime(currentTime, expiredTime));
    }

    private long calcExpiredTime(long currentTime, long expiredTime) {
        return currentTime + expiredTime;
    }

    private String createToken(String subject, long expiredTime) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(new Date(expiredTime))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    @Override
    public RefreshResponse refreshAccessToken(RefreshAccessTokenServiceRequest serviceRequest) {
        Claims verifyResult = verifyAndGet(serviceRequest.refreshToken());

        return new RefreshResponse(BEARER_TOKEN_PREFIX + createToken(verifyResult.getSubject(),
            serviceRequest.currentTime()), serviceRequest.refreshToken());
    }

    private Claims verifyAndGet(String refreshToken) {
        Claims verify = verifyToken(refreshToken);

        if (jwtRepository.findByJwtEntity(new JwtEntity(refreshToken, JwtType.REFRESH_TOKEN))
            .isEmpty()) {
            throw new JwtExpiredException();
        }

        return verify;
    }

    @Override
    public String createRefreshToken(String subject, long currentTime) {
        long expiredTime = Long.parseLong(REFRESH_EXPIRED_TIME);

        String refreshToken = createToken(subject, calcExpiredTime(currentTime, expiredTime));
        jwtRepository.save(new JwtEntity(refreshToken, JwtType.REFRESH_TOKEN), expiredTime);

        return BEARER_TOKEN_PREFIX + refreshToken;
    }

    @Override
    public Claims verify(String token) {
        return verifyToken(extractToken(token));
    }

    private Claims verifyToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new JwtDeniedException();
        }
    }

    private static String extractToken(String token) {
        if (token.contains(BEARER_TOKEN_PREFIX)) {
            return token.substring(BEARER_TOKEN_PREFIX.length());
        }
        return token;
    }
}
