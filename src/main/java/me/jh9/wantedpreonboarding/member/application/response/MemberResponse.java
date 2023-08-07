package me.jh9.wantedpreonboarding.member.application.response;

import me.jh9.wantedpreonboarding.member.domain.Member;

public record MemberResponse(
    Long id
) {

    public static MemberResponse toDto(Member member) {
        return new MemberResponse(member.getId());
    }
}
