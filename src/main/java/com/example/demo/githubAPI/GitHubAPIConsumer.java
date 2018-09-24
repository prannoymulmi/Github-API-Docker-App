package com.example.demo.githubAPI;

import com.example.demo.constants.GitHubAPIConstants;
import com.example.demo.dto.GithubUserApiDTO;
import com.example.demo.dto.GithubUserTotalDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubAPIConsumer implements IGitHubAPIConsumer {
    private final String PAGE_QUERY_KEY = "page";

    @Override
    public String getGitHubUserByProgrammingLanguage(String language, int page) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(GitHubAPIConstants.GIT_HUB_BASE_URL)
            .queryParam(PAGE_QUERY_KEY, page)
            .query("q=language:{Lang}").buildAndExpand(language);
        return mapStringToDtoAndReturn(builder);
    }

    private String mapStringToDtoAndReturn(UriComponents builder) {
        RestTemplate restTemplate = new RestTemplate();
        GithubUserTotalDTO unprocessedResults = restTemplate.getForObject(builder.toUriString(), GithubUserTotalDTO.class);
        return getUserDataInJsonString(unprocessedResults);
    }

    private String getUserDataInJsonString(GithubUserTotalDTO unprocessedResults) {
        List<GithubUserApiDTO> processedUsers = new ArrayList<>();

        unprocessedResults.getGithubUserApiDTOS().forEach( res -> {
            RestTemplate restTemplate = new RestTemplate();
            UriComponents builderUser = UriComponentsBuilder.fromHttpUrl(res.getUrl()).build();
            GithubUserApiDTO user = restTemplate.getForObject(builderUser.toUriString(), GithubUserApiDTO.class);
            processedUsers.add(user);
        });

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(processedUsers);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }
}
