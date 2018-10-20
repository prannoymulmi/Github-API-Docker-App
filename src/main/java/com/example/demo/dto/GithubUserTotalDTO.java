package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GithubUserTotalDTO {
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("items")
    private Optional<List<GithubUserApiDTO>> githubUserApiDTOS;
}
