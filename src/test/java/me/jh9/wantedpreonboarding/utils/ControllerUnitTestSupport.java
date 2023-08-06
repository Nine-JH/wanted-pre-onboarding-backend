package me.jh9.wantedpreonboarding.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jh9.wantedpreonboarding.article.api.ArticleController;
import me.jh9.wantedpreonboarding.article.application.ArticleService;
import me.jh9.wantedpreonboarding.common.jwt.api.JwtController;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.member.api.MemberController;
import me.jh9.wantedpreonboarding.member.application.MemberService;
import me.jh9.wantedpreonboarding.utils.config.UnitTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(UnitTestConfig.class)
@WebMvcTest(
    controllers = {MemberController.class, JwtController.class, ArticleController.class},
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
public abstract class ControllerUnitTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected ArticleService articleService;

    @MockBean
    protected JwtService jwtService;
}
