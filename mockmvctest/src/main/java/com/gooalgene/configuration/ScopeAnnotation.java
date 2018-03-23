package com.gooalgene.configuration;

import com.gooalgene.annotation.HelloMessageGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by crabime on 3/8/18.
 */
@Configuration
public class ScopeAnnotation {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HelloMessageGenerator requestMessage() {
        return new HelloMessageGenerator();
    }
}
