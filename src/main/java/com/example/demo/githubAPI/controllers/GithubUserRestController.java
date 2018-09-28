package com.example.demo.githubAPI.controllers;

import com.example.demo.githubAPI.IGitHubAPIConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class GithubUserRestController {
    private IGitHubAPIConsumer gitHubAPIConsumer;

    /**
     * Constructor injection preferred instead of the other types because it insures the paremeter is
     * initialized when the class is created to
     */
    @Autowired
    public GithubUserRestController(IGitHubAPIConsumer gitHubAPIConsumer) {
        this.gitHubAPIConsumer = gitHubAPIConsumer;
    }

    /**
     * Caching done with number and language so that key is unique for the page and language
     * TODO: Implement the page_items and add it in the cache key
     * @param language
     * @param page
     * @param pageItems
     * @return
     */
    @RequestMapping(value = "/byLanguage", method = RequestMethod.GET)
    @Cacheable(value = "post-single", key = "#page + #language")
    public ResponseEntity<String> getUsers(@RequestParam(value = "language") String language,
        @RequestParam(value = "page") int page,
        @RequestParam(value = "page_items", required = false) String pageItems) {

        String json = gitHubAPIConsumer.getGitHubUserByProgrammingLanguage(language, page);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
