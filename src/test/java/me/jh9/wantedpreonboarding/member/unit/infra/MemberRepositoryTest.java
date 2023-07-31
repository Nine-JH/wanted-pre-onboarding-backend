package me.jh9.wantedpreonboarding.member.unit.infra;

import java.util.NoSuchElementException;
import java.util.Optional;
import me.jh9.wantedpreonboarding.member.domain.Member;
import me.jh9.wantedpreonboarding.member.infra.MemberRepository;
import me.jh9.wantedpreonboarding.utils.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("findByEmail(String email) 은")
    @Nested
    class Context_findByEmail{
        @DisplayName("DB에 검색대상 email을 가진 사용자가 존재할 때 Member를 가져올 수 있다.")
        @Test
        void _willSuccess(){
            // given
            Member newMember = Member.createNewMember("test@email.com", "password");
            Member savedMember = memberRepository.save(newMember);

            // when
            Optional<Member> optionalMember = memberRepository.findByEmail(newMember.getEmail());

            // then
            Assertions.assertThat(optionalMember).isPresent();
            Assertions.assertThat(optionalMember.get()).extracting("id", "email", "password")
                .containsExactly(newMember.getId(), newMember.getEmail(), newMember.getPassword());
        }

        @DisplayName("DB에 검색대상 email을 가진 사용자가 없다면 빈 Optional<Member>가 반환된다.")
        @Test
        void nowhereEmail_willFail(){
            // given
            String nowhereEmail = "test1@email.com";
            Member newMember = Member.createNewMember("test@email.com", "password");
            memberRepository.save(newMember);

            // when
            Optional<Member> optionalMember = memberRepository.findByEmail(nowhereEmail);

            // then
            Assertions.assertThat(optionalMember).isEmpty();
            Assertions.assertThatThrownBy(optionalMember::get)
                .isInstanceOf(NoSuchElementException.class);
        }
    }
}
