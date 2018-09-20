package com.example.demo.githubAPI;

import com.example.demo.constants.GitHubAPIConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GitHubAPIConsumer implements IGitHubAPIConsumer {
    private final String PAGE_QUERY_KEY = "page";
    @Override
    public String getGitHubUserByProgrammingLanguage(String language, int page) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(GitHubAPIConstants.GIT_HUB_BASE_URL)
            .queryParam(PAGE_QUERY_KEY, page)
            .query("q=language:{Lang}").buildAndExpand(language);
        String result = restTemplate.getForObject(builder.toUriString(), String.class);
        return result;
    }
}
