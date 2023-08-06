package me.jh9.wantedpreonboarding.utils.member;

import me.jh9.wantedpreonboarding.member.domain.Member;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestMemberFactory {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NotNull
    public static Member createTestMember() {
        return Member.createNewMember("email@test.com", passwordEncoder.encode("password"));
    }
}
