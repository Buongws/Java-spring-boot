package com.ecommerce.project.config;

import jakarta.servlet.Servlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.h2.server.web.JakartaWebServlet")
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true")
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<Servlet> h2ConsoleServletRegistration() {
        try {
            Servlet servlet = (Servlet) Class.forName("org.h2.server.web.JakartaWebServlet")
                    .getDeclaredConstructor()
                    .newInstance();

            ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<>(
                    servlet,
                    "/h2-console",
                    "/h2-console/*"
            );
            registration.setName("H2Console");
            registration.addInitParameter("trace", "false");
            registration.addInitParameter("webAllowOthers", "false");
            registration.setLoadOnStartup(1);
            return registration;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to register H2 console servlet", exception);
        }
    }
}
