package me.jh9.wantedpreonboarding.member.application.request;

public record LoginServiceRequest(
    String email,
    String password
) {

}
