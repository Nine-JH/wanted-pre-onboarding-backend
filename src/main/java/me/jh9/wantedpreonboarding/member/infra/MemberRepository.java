package me.jh9.wantedpreonboarding.member.infra;

import java.util.Optional;
import me.jh9.wantedpreonboarding.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
