package griezma.mssc.beerservice.api.mapper;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.services.inventory.BeerInventoryService;
import griezma.mssc.beerservice.api.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest({ BeerMapper.class, DateTimeMapper.class })
public class BeerMapperTest {
    @Autowired
    private BeerMapper mapper;

    @MockBean
    private BeerInventoryService inventoryMock;

    @BeforeEach
    void inventoryServiceStub() {
        Mockito.when(inventoryMock.getOnhandInventory(any(UUID.class)))
                .thenReturn(42);
    }

    @Test
    void mapsBeerToDtoAndBackWithoutLoss() {
        Beer validBeer = validBeer();
        BeerDto dto = mapper.beerToDto(validBeer);
        Beer mappedBeer = mapper.dtoToBeer(dto);
        assertEquals(validBeer, mappedBeer);
    }

    @Test
    void beerDtoContainsInventoryData() {
        Beer validBeer = validBeer();
        BeerDto dto = mapper.beerToDto(validBeer);
        assertEquals(42, dto.getQuantityOnHand());
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
