package me.jh9.wantedpreonboarding.utils;

import me.jh9.wantedpreonboarding.article.application.ArticleService;
import me.jh9.wantedpreonboarding.article.infra.ArticleRepository;
import me.jh9.wantedpreonboarding.member.application.MemberService;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import me.jh9.wantedpreonboarding.utils.config.UnitTestConfig;
import me.jh9.wantedpreonboarding.utils.fake.JwtFakeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(UnitTestConfig.class)
@ActiveProfiles("test")
@SpringBootTest
public abstract class UnitTestSupport {

    @InjectMocks
    protected ArticleService articleService;

    @Mock
    protected ArticleRepository articleRepository;

    @InjectMocks
    protected MemberService memberService;

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected JwtFakeService jwtFakeService;
}
