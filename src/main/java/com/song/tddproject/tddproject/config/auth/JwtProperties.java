package com.song.tddproject.tddproject.config.auth;

public interface JwtProperties {
    String SECRET = "TDD_PROJECT";

    int EXPIRATION_TIME = 864000000;

    String TOKEN_PREFIX = "Bearer ";

    String HEADER_STRING = "Authorization";
}
