# Auth-Service

- 회원가입.
- 로그인 시 AccessToken/RefreshToken 생성.
  - AccessToken 은 Response 로 반환.
  - RefreshToken 은 Cookie 에 저장.
- AccessToken 이 만료되었을 시 DB 에서 RefreshToken 검증 후 새로운 AccessToken 반환.
