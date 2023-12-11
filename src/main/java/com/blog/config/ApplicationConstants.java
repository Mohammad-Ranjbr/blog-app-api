package com.blog.config;

public class ApplicationConstants {

    public static final String SECRET = "jwtTokenForBlogAppApi";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public static final String PAGE_NUMBER="0";
    public static final String PAGE_SIZE="2";
    public static final String SORT_BY="postId";
    public static final String SORT_DIR="asc";
    public static final Integer ADMIN_USER=501;
    public static final Integer NORMAL_USER=502;
    public static final String CATEGORY_TITLE_SIZE = "Category title must be min of 4 characters and max of 50 characters";
    public static final String CATEGORY_DESCRIPTION_SIZE = "Category description must be min of 10 characters and max of 250 characters";
    public static final String USER_NAME_SIZE = "Username must be min of 4 characters";
    public static final String USER_EMAIL_ERROR = "Email address is not valid";
    public static final String USER_PASSWORD_SIZE = "Password must be min of 4 characters and max of 10 characters";
    public static final String ACCESS_DENIED = "Access Denied";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_TOKEN_ILLEGAL_ARGUMENT_EXCEPTION = "Unable to get jwt token";
    public static final String JWT_TOKEN_EXPIRED_JWT_EXCEPTION = "Jwt token has expired";
    public static final String JWT_TOKEN_MALFORMED_JWT_EXCEPTION = "Invalid jwt token";
    public static final String NULL_JWT_TOKEN = "Jwt token is null or does not begin with Bearer";
    public static final String JWT_TOKEN_STARTS_WITH = "Bearer";
    public static final String JWT_TOKEN_FAILED_VALIDATE = "Invalid jwt token";
    public static final String JWT_TOKEN_NULL_USERNAME_OR_CONTEXT = "Username is null or security context is null";

}
