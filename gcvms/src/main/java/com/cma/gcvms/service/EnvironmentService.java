package com.cma.gcvms.service;

import static com.cma.gcvms.service.EnvironmentService.Environment.Development;
import static com.cma.gcvms.service.EnvironmentService.Environment.Integration;
import static com.cma.gcvms.service.EnvironmentService.Environment.Production;
import static com.cma.gcvms.service.EnvironmentService.Environment.toEnvironment;
import static org.apache.commons.lang.StringUtils.trimToEmpty;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

@Named
public class EnvironmentService {
    @Value("${env_name:development}")
    private String environmentName;

    public enum Environment {
        Development, Integration, Production;
        boolean is(String value) {
            return name().equalsIgnoreCase(trimToEmpty(value));
        }

        public static Environment toEnvironment(String value) {
            for (Environment environment : values()) {
                if (environment.is(value)) {
                    return environment;
                }
            }
            return Development;
        }
    }

    public boolean isDevelopment() {
        return Development.is(environmentName);
    }

    public boolean isIntegration() {
        return Integration.is(environmentName);
    }

    public boolean isProduction() {
        return Production.is(environmentName);
    }

    public Environment getEnvironment() {
        return toEnvironment(environmentName);
    }
}
