package me.jh9.wantedpreonboarding.unit.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import me.jh9.wantedpreonboarding.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("createNewMember() 는")
    @Nested
    class Context_CreateNewMember {

        @DisplayName("적절한 값 입력 시 새로운 Member 생성이 가능하다.")
        @Test
        void _willSuccess() {
            // given
            String correctEmail = "test@email.com";
            String correctPassword = "password";

            // when
            Member newMember = Member.createNewMember(correctEmail, correctPassword);

            // then
            Assertions.assertThat(newMember).extracting("email", "password")
                .containsExactly(correctEmail, correctPassword);

            Assertions.assertThat(newMember.getId()).isNull();
        }
    }
}
