package griezma.mssc.beerservice.services.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import griezma.mssc.brewery.model.BeerInventoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(InventoryServiceClient.class)
public class InventoryServiceClientTest {
    @Autowired
    InventoryServiceClient client;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    ObjectMapper json;

    final UUID beerId = UUID.randomUUID();

    @Test
    void worksForExistingBeer() throws Exception {
        String jsonResult = json.writeValueAsString(beerInventoryList());

        server.expect(requestTo(endsWith(InventoryServiceClient.INVENTORY_PATH.replace("{beerId}", beerId.toString()))))
                .andRespond(withSuccess(jsonResult, MediaType.APPLICATION_JSON));

        Integer result = client.getOnhandInventory(beerId);

        assertEquals(102, result);
    }

    @Test
    void resolvesToZeroForUnknownBeer() {
        server.expect(requestTo(endsWith(InventoryServiceClient.INVENTORY_PATH.replace("{beerId}", beerId.toString()))))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));
        Integer result = client.getOnhandInventory(beerId);
        assertThat(result).isZero();
    }

    List<BeerInventoryDto> beerInventoryList() {
        return List.of(
                BeerInventoryDto.builder()
                        .beerId(beerId)
                        .quantityOnHand(99)
                        .build(),
                BeerInventoryDto.builder()
                        .beerId(beerId)
                        .quantityOnHand(3)
                        .build());
    }
}
