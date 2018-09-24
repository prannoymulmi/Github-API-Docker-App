package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUserTotalDTO {
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("items")
    private List<GithubUserApiDTO> githubUserApiDTOS;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<GithubUserApiDTO> getGithubUserApiDTOS() {
        return githubUserApiDTOS;
    }

    public void setGithubUserApiDTOS(List<GithubUserApiDTO> githubUserApiDTOS) {
        this.githubUserApiDTOS = githubUserApiDTOS;
    }
}
