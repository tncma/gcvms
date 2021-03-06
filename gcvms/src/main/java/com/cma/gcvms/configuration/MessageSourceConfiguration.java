
package com.cma.gcvms.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfiguration {
    /**
     * Base message source to handle internationalization<p>
     * Order of basenames matters, the first found property is returned 
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames( // 
                // main resources
                "classpath:/localization/application", "classpath:/localization/messages", //
                // pages
                "classpath:/localization/pages/home", //
                "classpath:/localization/pages/login", //
                "classpath:/localization/pages/concurrentModificationResolution", //
                // default spring security messages
                "classpath:org/springframework/security/messages", //
                //  our bean validation messages
                "classpath:ValidationMessages", //
                // default conversion messages
                "classpath:javax/faces/Messages", //
                // generated domain resources
                "classpath:/localization/domain/DailyTrip", //
                "classpath:/localization/domain/Dept", //
                "classpath:/localization/domain/Employee", //
                "classpath:/localization/domain/Municipality", //
                "classpath:/localization/domain/Route", //
                "classpath:/localization/domain/TripSchedule", //
                "classpath:/localization/domain/Vehicle", //
                "classpath:/localization/domain/VehicleTyp", //
                // default bean validation messages 
                "classpath:org/hibernate/validator/ValidationMessages" //
        );
        return messageSource;
    }
}
