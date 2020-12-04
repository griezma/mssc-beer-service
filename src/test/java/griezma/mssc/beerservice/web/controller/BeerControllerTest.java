package griezma.mssc.beerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import griezma.mssc.beerservice.web.model.BeerDto;
import griezma.mssc.beerservice.web.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper json;

    @Test
    void canGetBeerById() throws Exception {
        mvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()))
                .andExpect(header().string("Content-type", "application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void canPostBeer() throws Exception {
        mvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsBytes(BeerCreation.validBeer())))
//                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated());
    }

    @Test
    void canDeleteBeer() throws Exception {
        mvc.perform(delete("/api/v1/beer/" + UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());
    }
}

class BeerCreation {
    static BeerDto validBeer() {
        return BeerDto.builder()
                .beerName("Bad Goose")
                .beerStyle(BeerStyle.PORTER)
                .quantityOnHand(500)
                .price(BigDecimal.valueOf(11))
                .build();
    }
}
