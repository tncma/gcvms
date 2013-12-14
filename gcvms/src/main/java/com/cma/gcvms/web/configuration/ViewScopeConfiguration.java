package com.cma.gcvms.web.configuration;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cma.gcvms.web.faces.ConversationContextScope;
import com.cma.gcvms.web.faces.ConversationScope;
import com.cma.gcvms.web.faces.ViewScope;

@Configuration
public class ViewScopeConfiguration {
    @Bean
    public static CustomScopeConfigurer viewScope() {
        CustomScopeConfigurer scope = new CustomScopeConfigurer();
        Map<String, Object> map = newHashMap();
        map.put("view", new ViewScope());
        map.put("conversationContext", new ConversationContextScope());
        map.put("conversation", new ConversationScope());
        scope.setScopes(map);
        return scope;
    }
}