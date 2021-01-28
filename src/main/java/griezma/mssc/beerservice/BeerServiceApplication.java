package griezma.mssc.beerservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class BeerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeerServiceApplication.class, args);
    }

//    @Bean
//    ApplicationRunner runner(Environment env) {
//        return args -> {
//            log.info("Using database " + env.getProperty("spring.jpa.database"));
//            log.info("Config source is " + env.getProperty("beerworks.configsource.id"));
//        };
//    }
}
