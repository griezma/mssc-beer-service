package griezma.mssc.beerservice.data.mapper;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest({ BeerMapper.class, DateTimeMapper.class })
public class BeerMapperTest {
    @Autowired
    private BeerMapper mapper;

    @Test
    void mapsBeerToDtoAndBackWithoutLoss() {
        Beer validBeer = validBeer();
        BeerDto dto = mapper.beerToDto(validBeer);
        Beer mappedBeer = mapper.dtoToBeer(dto);
        assertEquals(validBeer, mappedBeer);
    }

    Beer validBeer() {
        return Beer.builder()
                .id(UUID.randomUUID())
                .beerName("foo")
                .beerStyle("ALE")
                .price(BigDecimal.TEN)
                .upc("07411")
                .lastModified(Timestamp.from(Instant.now()))
                .created(Timestamp.from(Instant.now()))
                .build();
    }
}
