package griezma.mssc.beerservice.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class BeerInventoryServiceClient implements BeerInventoryService {
    private static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private final RestTemplate restTemplate;

    // NOTE: must not be final
    @Value("${brewery.inventory_service_host}")
    private String inventoryServiceHost = "http://localhost:8080";

    public BeerInventoryServiceClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        String url = inventoryServiceHost + INVENTORY_PATH;
        //log.debug("getOnhandInventory url={}, beerId={}", url, beerId);
        var responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BeerInventoryDto>>() {}, beerId);
        return responseEntity.getBody().stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
    }
}
