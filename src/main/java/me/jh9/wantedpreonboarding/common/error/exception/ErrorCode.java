package me.jh9.wantedpreonboarding.common.error.exception;

public enum ErrorCode {

    A001("존재하지 않는 게시글"),
    A002("게시글 권한 없음"),

    M001("로그인 정보 불일치"),
    M002("존재하지 않는 회원"),
    M003("이메일 중복"),

    T001("토큰 만료"),
    T002("미인증 토큰"),

    B001("입력 오류"),

    S001("서버 오류")
    ;

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
