package griezma.mssc.beerservice.services.inventory;

import griezma.mssc.brewery.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Profile("!cloudconfig")
@Slf4j
@Component
public class InventoryServiceRestClient implements InventoryServiceClient {
    private final RestTemplate restTemplate;

    // NOTE: must not be final
    @Value("${beerworks.inventory_service_base:http://localhost:9090}")
    private String inventoryServiceBase;

    public InventoryServiceRestClient(RestTemplateBuilder builder,
                                      @Value("${beerworks.inventory-user:better}") String user,
                                      @Value("${beerworks.inventory-password:beer}") String password) {
        this.restTemplate = builder
                .basicAuthentication(user, password)
                .build();
    }

    @Override
    public List<BeerInventoryDto> getOnhandInventoryList(UUID beerId) {
        String url = inventoryServiceBase + INVENTORY_PATH;
        log.trace("getOnhandInventory: {}", beerId);
        var responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BeerInventoryDto>>() {}, beerId);
        return responseEntity.getBody();
    }
}
