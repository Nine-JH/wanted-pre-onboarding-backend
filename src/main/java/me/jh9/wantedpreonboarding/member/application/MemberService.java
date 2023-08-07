package me.jh9.wantedpreonboarding.member.application;

import me.jh9.wantedpreonboarding.common.jwt.application.usecase.AccessTokenUseCase;
import me.jh9.wantedpreonboarding.common.jwt.application.usecase.RefreshTokenUseCase;
import me.jh9.wantedpreonboarding.member.application.request.LoginServiceRequest;
import me.jh9.wantedpreonboarding.member.application.request.SignUpServiceRequest;
import me.jh9.wantedpreonboarding.member.application.response.LoginResponse;
import me.jh9.wantedpreonboarding.member.application.response.LoginResponse.JwtFair;
import me.jh9.wantedpreonboarding.member.application.response.MemberResponse;
import me.jh9.wantedpreonboarding.member.application.usecase.LoginUseCase;
import me.jh9.wantedpreonboarding.member.application.usecase.SignUpUseCase;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService implements SignUpUseCase, LoginUseCase {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenUseCase accessTokenUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public MemberService(MemberRepository memberRepository, AccessTokenUseCase accessTokenUseCase,
        RefreshTokenUseCase refreshTokenUseCase) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.accessTokenUseCase = accessTokenUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @Transactional
    @Override
    public MemberResponse signUp(SignUpServiceRequest request) {

        validateEmailExist(request);

        Member savedMember = memberRepository.save(
            Member.createNewMember(request.email(), passwordEncoder.encode(request.password())));

        return MemberResponse.toDto(savedMember);
    }

    private void validateEmailExist(SignUpServiceRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
    }

    @Override
    public LoginResponse login(LoginServiceRequest loginRequest) {
        final String email = loginRequest.email();
        final String inputPassword = loginRequest.password();

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 로그인 정보입니다."));

        validatePasswordMatch(inputPassword, member.getPassword());

        return LoginResponse.toDto(member.getId(), createJwtFair(member.getId()));
    }

    private void validatePasswordMatch(String inputPassword, String realPassword) {
        if (!passwordEncoder.matches(inputPassword, realPassword)) {
            throw new IllegalArgumentException("잘못된 로그인 정보입니다.");
        }
    }

    private JwtFair createJwtFair(Long memberId) {
        String subject = String.valueOf(memberId);
        String accessToken = accessTokenUseCase.createAccessToken(subject, System.currentTimeMillis());
        String refreshToken = refreshTokenUseCase.createRefreshToken(subject, System.currentTimeMillis());

        return new JwtFair(accessToken, refreshToken);
    }
}
