package me.jh9.wantedpreonboarding.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import me.jh9.wantedpreonboarding.common.jwt.api.JwtController;
import me.jh9.wantedpreonboarding.member.api.MemberController;
import me.jh9.wantedpreonboarding.member.application.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = {MemberController.class, JwtController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected JwtService jwtService;
}
