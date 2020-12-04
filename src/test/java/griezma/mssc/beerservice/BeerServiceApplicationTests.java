package griezma.mssc.beerservice;

import griezma.mssc.beerservice.data.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeerServiceApplicationTests {
    @Autowired
    BeerRepository repo;

    @Test
    void contextLoads() {
        assertNotNull(repo);
    }

}
