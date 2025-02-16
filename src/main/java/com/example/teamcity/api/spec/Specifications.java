package com.example.teamcity.api.spec;

import com.example.teamcity.api.models.User;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.util.List;
import com.example.teamcity.api.config.Config;

public class Specifications {
    private static Specifications spec;

    private Specifications() {};

    public static Specifications getSpec() {
        if(spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder requestBuilder() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.setAccept(ContentType.JSON);
        reqBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        return reqBuilder;
    }

    public RequestSpecification unauthSpec() {
        String host = Config.getProperty("host");
        String baseUri = "http://" + host;

        RequestSpecBuilder reqBuilder = requestBuilder();
        reqBuilder.setBaseUri(baseUri);

        return reqBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.setAccept(ContentType.JSON);
        reqBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        reqBuilder.setBaseUri("http://" + user.getUser() + ":" + user.getPassword() + "@" + Config.getProperty("host"));
        return reqBuilder.build();
    }
}