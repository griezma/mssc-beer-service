package griezma.mssc.beerservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import griezma.mssc.beerservice.api.model.BeerDto;
import griezma.mssc.beerservice.api.model.BeerStyle;
import griezma.mssc.beerservice.services.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper json;

    @MockBean
    BeerService mockBeerService;

    @Test
    void getsExistingBeerById() throws Exception {
        Mockito.when(mockBeerService.findBeerById(any(UUID.class), anyBoolean()))
                .thenAnswer(invocation -> Optional.of(validBeer(invocation.getArgument(0))));
        mvc.perform(get("/api/v1/beer/" + UUID.randomUUID()))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnsNotFoundIfBeerNotExists() throws Exception {
        Mockito.when(mockBeerService.findBeerById(any(UUID.class), anyBoolean()))
                .thenReturn(Optional.empty());

        mvc.perform(get("/api/v1/beer/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void canSaveNewBeer() throws Exception {
        BeerDto validDto = validBeer(null);

        Mockito.when(mockBeerService.saveBeer(validDto))
                .thenAnswer(i -> { validDto.setId(UUID.randomUUID()); return validDto; });

        mvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsBytes(validDto)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void rejectsSavingInvalidBeer() throws Exception {
        BeerDto invalidBeer = invalidBeer(null);

        Mockito.when(mockBeerService.saveBeer(invalidBeer))
                .thenAnswer(i -> { invalidBeer.setId(UUID.randomUUID()); return invalidBeer; });

        mvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsBytes(invalidBeer)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void canDeleteBeer() throws Exception {
        mvc.perform(delete("/api/v1/beer/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    void canUpdateBeer() throws Exception {
        BeerDto validBeer = validBeer(null);
        BeerDto changedBeer = validBeer(null);
        changedBeer.setBeerName("other beer");
        UUID fakeId = UUID.randomUUID();

        Mockito.when(mockBeerService.findBeerById(fakeId, false))
                .thenReturn(Optional.of(validBeer));

        mvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                .content(json.writeValueAsBytes(changedBeer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    static BeerDto validBeer(UUID id) {
        return BeerDto.builder()
                .id(id)
                .beerName("Bad Goose")
                .beerStyle(BeerStyle.PORTER)
                .quantityOnHand(500)
                .price(BigDecimal.valueOf(2.99))
                .build();
    }

    static BeerDto invalidBeer(UUID id) {
        return BeerDto.builder()
                .id(id)
                .beerName(null)
                .beerStyle(BeerStyle.PORTER)
                .quantityOnHand(500)
                .price(BigDecimal.valueOf(3.99))
                .build();
    }
}
