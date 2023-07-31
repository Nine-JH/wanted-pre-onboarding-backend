package me.jh9.wantedpreonboarding.member.application.request;


public record SignUpServiceRequest(
    String email,
    String password
) {
}
