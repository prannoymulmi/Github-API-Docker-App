package com.example.demo;

import com.example.demo.aop.GithubAPIRestControllerErrorHandler;
import com.example.demo.dto.ErrorDTO;
import com.example.demo.githubAPI.GitHubAPIConsumer;
import com.example.demo.githubAPI.controllers.GithubUserRestController;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;


@RunWith(MockitoJUnitRunner.class)
public class GithubAPIRestControllerErrorHandlerTest {
    @InjectMocks
    GithubUserRestController githubUserRestController;
    @Mock
    GitHubAPIConsumer gitHubAPIConsumer;
    private AspectJProxyFactory factory;
    private GithubAPIRestControllerErrorHandler githubAPIRestControllerErrorHandler;
    private GithubUserRestController proxy;

    @Before
    public void setUp() {
        factory = new AspectJProxyFactory(githubUserRestController);
        // Configuring my aspect
        GithubAPIRestControllerErrorHandler githubAPIRestControllerErrorHandler = new GithubAPIRestControllerErrorHandler();
        factory.addAspect(githubAPIRestControllerErrorHandler);
        proxy = factory.getProxy();
    }

    @Test
    public void handleGitHubRestControllerException_HTTPClientNotFoundExceptionThrown_HttpStatusNotFound() {
        // Given

        // When
        Mockito.when(gitHubAPIConsumer.getGitHubUserByProgrammingLanguage(StringUtils.EMPTY, 0))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseEntity<String> result = proxy.getUsers(StringUtils.EMPTY, 0);

        // Then
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void handleGitHubRestControllerException_Exception_StatusCode500() {
        // Given
        final String ERROR_MSG_BODY = "This is a test";
        ErrorDTO expectedError = new ErrorDTO();

        // When
        Mockito.when(gitHubAPIConsumer.getGitHubUserByProgrammingLanguage(StringUtils.EMPTY, 0))
            .thenThrow(new IllegalArgumentException(ERROR_MSG_BODY));
        expectedError.setErrorMsg(ERROR_MSG_BODY);
        ResponseEntity<String> result = proxy.getUsers(StringUtils.EMPTY, 0);

        // Then
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        Assert.assertEquals(expectedError.toString(), result.getBody());
    }

    @Test
    public void handleGitHubRestControllerException_NoException_StatusCode200() {
        // Given
        final String RESULT =  "[{'test': 'test'}]";

        // When
        Mockito.when(gitHubAPIConsumer.getGitHubUserByProgrammingLanguage(StringUtils.EMPTY, 0))
            .thenReturn(RESULT);
        ResponseEntity<String> result = proxy.getUsers(StringUtils.EMPTY, 0);

        // Then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(RESULT, result.getBody());
    }
}
