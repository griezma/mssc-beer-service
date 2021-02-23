package griezma.mssc.beerservice.services;

import griezma.mssc.brewery.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class BeerServiceTest {
    @Autowired
    BeerService beerService;

    @Test
    void canQueryByName() {
        Page<BeerDto> result = beerService.listBeers("mango", null, false, PageRequest.of(0, 5));
        assertThat(result.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void canQueryByStyle() {
        Page<BeerDto> result = beerService.listBeers(null, "IPA", false, PageRequest.of(0, 5));
        assertThat(result.getTotalElements()).isGreaterThan(0);
    }
}
