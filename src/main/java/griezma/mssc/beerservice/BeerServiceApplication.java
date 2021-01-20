package griezma.mssc.beerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableFeignClients
//@EnableAsync
public class BeerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeerServiceApplication.class, args);
    }
}
