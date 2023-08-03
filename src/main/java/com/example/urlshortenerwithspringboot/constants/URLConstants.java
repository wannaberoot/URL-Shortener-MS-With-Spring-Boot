package com.example.urlshortenerwithspringboot.constants;

public class URLConstants {

    public static final String BASE_REQUEST_PATH = "/api/v1";
    public static final String URL_PATH = "/url";
    public static final String USER_PATH = "/user";
    public static final String SHORT_URL_PATH = "/{shortURL}";
    public static final String USERNAME_PATH = "/{username}";
    public static final String ID_PATH = "/{id}";
    public static final String URL_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String AWS_URL = "http://a46bf89f8a838451b954f0d3ddacdf48-9429341.us-east-2.elb.amazonaws.com";
    public static final String ROOT_URL = AWS_URL + BASE_REQUEST_PATH + "/";
}
