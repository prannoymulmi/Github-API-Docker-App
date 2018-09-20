package com.example.demo.utils;

import org.springframework.lang.Nullable;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomUrlBuilder {
    @Nullable
    private String url;

    public void fromHttpUrl(String url) {
        this.url = url;
    }

    public void queryWithColon(String queryKey, String queryValue){
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
            .query("q={queryKey}:{queryValue}").buildAndExpand(queryKey, queryValue);
        this.url = builder.toUriString();
    }

    public void query(String query, Object val){
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(query, val).build();
        url = builder.toUriString();
    }

    public String toUriString(){
        return url;
    }

    public CustomUrlBuilder build() {
        return this;
    }
}
