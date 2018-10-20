package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
public class GithubUserApiDTO {
    @JsonProperty("login")
    private String login;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String name;
    private String url;
}
