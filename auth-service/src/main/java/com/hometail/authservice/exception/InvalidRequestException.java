package com.hometail.authservice.exception;

public class InvalidRequestException extends RuntimeException {

    public static InvalidRequestException DuplicatedEmail = new InvalidRequestException("이메일이 유효하지 않습니다.");
    public static InvalidRequestException NotExistsEmail = new InvalidRequestException("이메일이 존재하지 않습니다.");
    public static InvalidRequestException NotExistsId = new InvalidRequestException("계정이 존재하지 않습니다.");

    public static InvalidRequestException InvalidPassword = new InvalidRequestException("비밀번호가 유효하지 않습니다.");

    public static InvalidRequestException NotExistsAccessToken = new InvalidRequestException("엑세스 토큰이 존재하지 않습니다.");
    public static InvalidRequestException NotExistsRefreshToken = new InvalidRequestException("리프레시 토큰이 존재하지 않습니다.");
    public static InvalidRequestException BadRequest = new InvalidRequestException("잘못된 요청입니다.");

    public InvalidRequestException(String message) {
        super(message);
    }
}
