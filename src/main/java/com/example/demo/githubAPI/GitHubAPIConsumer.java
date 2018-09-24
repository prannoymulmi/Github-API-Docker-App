package com.example.demo.githubAPI;

import com.example.demo.constants.GitHubAPIConstants;
import com.example.demo.dto.GithubUserApiDTO;
import com.example.demo.dto.GithubUserTotalDTO;
import com.example.demo.utils.IDTOToJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GitHubAPIConsumer implements IGitHubAPIConsumer {
    private final String PAGE_QUERY_KEY = "page";
    private IDTOToJsonConverter dtoToJsonConverter;
    private RestTemplate restTemplate;
    private Optional<GithubUserTotalDTO> optionalUnprocessedResults;

    @Autowired
    public GitHubAPIConsumer(IDTOToJsonConverter dtoToJsonConverter, RestTemplate restTemplate) {
        this.dtoToJsonConverter = dtoToJsonConverter;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getGitHubUserByProgrammingLanguage(String language, int page) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(GitHubAPIConstants.GIT_HUB_BASE_URL)
            .queryParam(PAGE_QUERY_KEY, page)
            .query("q=language:{Lang}").buildAndExpand(language);
        return mapStringToDtoAndReturn(builder);
    }

    private String mapStringToDtoAndReturn(UriComponents builder) {
        GithubUserTotalDTO unprocessedResults = restTemplate.getForObject(builder.toUriString(), GithubUserTotalDTO.class);
        return processUserDataInJsonString(unprocessedResults);
    }

    private String processUserDataInJsonString(GithubUserTotalDTO unprocessedResults) {
        List<GithubUserApiDTO> processedUsers = new ArrayList<>();

        optionalUnprocessedResults = Optional.ofNullable(unprocessedResults);
        if(!optionalUnprocessedResults.isPresent()) {
            return dtoToJsonConverter.convert(GitHubAPIConstants.GIT_HUB_REQUEST_FAILURE_MESSAGE);
        }

        optionalUnprocessedResults
            .flatMap(GithubUserTotalDTO::getGithubUserApiDTOS)
            .ifPresent(unprocessedUsers -> {
                unprocessedUsers.forEach(res -> {
                    UriComponents builderUser = UriComponentsBuilder.fromHttpUrl(res.getUrl()).build();
                    GithubUserApiDTO user = restTemplate.getForObject(builderUser.toUriString(), GithubUserApiDTO.class);
                    processedUsers.add(user);
                });
            });

        return dtoToJsonConverter.convert(processedUsers);
    }
}
