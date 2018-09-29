package com.example.demo.aop;

import com.example.demo.constants.GitHubAPIConstants;
import com.example.demo.dto.ErrorDTO;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Aspect
@Configuration
public class GithubAPIRestControllerErrorHandler {

    /**
     * @Around advice used to have more flexibility as it can return the result compared
     * to the @AfterThrowing AOP advice. This method catches and handles the exception that occurs in the controller
     * @param p
     * @return
     */
    @Around("execution(* com.example.demo.githubAPI.controllers.*.*(..))")
    public ResponseEntity<String> handleGitHubRestControllerException(ProceedingJoinPoint p) {
        ErrorDTO error = new ErrorDTO();
        try{
            return (ResponseEntity<String>)p.proceed();
        } catch (Throwable ex) {
            Exception exception = new Exception(ex);
            boolean isHttpException = exception.getCause() instanceof HttpClientErrorException;
            if(isHttpException) {
               return handleHttpClientException(ex);
            }
            error.setErrorMsg(ex.getMessage());
            return new ResponseEntity<>(error.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> handleHttpClientException(Throwable ex) {
        ErrorDTO error = new ErrorDTO();
        HttpClientErrorException clientErrorException = (HttpClientErrorException) ex;
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error(clientErrorException.toString());
        error.setErrorMsg(clientErrorException.getMessage());
        return new ResponseEntity<>(error.toString(), clientErrorException.getStatusCode());
    }
}
