package griezma.mssc.beerservice.data;

import griezma.mssc.beerservice.services.BeerService;
import griezma.mssc.beerservice.api.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeerServiceTest {
    @Autowired
    BeerService beerService;

    final UUID existingBeerId = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");

    @Test
    void canFindBeerById() {
        var result = beerService.findBeerById(existingBeerId);
        assertThat(result).isNotEmpty();
    }

    @Test
    void canQueryByName() {
        Page<BeerDto> result = beerService.findBeers("mango", null, PageRequest.of(0, 5));
        assertThat(result.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void canQueryByStyle() {
        Page<BeerDto> result = beerService.findBeers(null, "IPA", PageRequest.of(0, 5));
        assertThat(result.getTotalElements()).isGreaterThan(0);
    }
}
