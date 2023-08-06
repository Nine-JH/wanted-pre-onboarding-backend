package me.jh9.wantedpreonboarding.utils;

import me.jh9.wantedpreonboarding.article.infra.ArticleRepository;
import me.jh9.wantedpreonboarding.common.jwt.infra.RedisJwtRepository;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public abstract class RepositoryTestSupport {

    @Autowired
    RedisJwtRepository jwtRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ArticleRepository articleRepository;
}
