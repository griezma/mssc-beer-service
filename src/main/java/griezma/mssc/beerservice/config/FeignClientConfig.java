package griezma.mssc.beerservice.config;

import feign.auth.BasicAuthRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignClientConfig {
    @Bean
    BasicAuthRequestInterceptor basicAuthInterceptor(@Value("${beerworks.inventory-user}") String user,
                                                        @Value("${beerworks.inventory-password}") String password) {
        log.debug("Feign auth interceptor picked up");
        return new BasicAuthRequestInterceptor(user, password);
    }
}
