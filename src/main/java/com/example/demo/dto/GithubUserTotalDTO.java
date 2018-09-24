package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUserTotalDTO {
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("items")
    private Optional<List<GithubUserApiDTO>> githubUserApiDTOS;

    public int getTotalCount() {
        return totalCount;
    }

    public Optional<List<GithubUserApiDTO>> getGithubUserApiDTOS() {
        return githubUserApiDTOS;
    }

}
