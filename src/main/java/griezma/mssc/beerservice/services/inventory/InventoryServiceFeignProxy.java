package griezma.mssc.beerservice.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Profile("localdiscovery")
@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryServiceFeignProxy implements InventoryService {

    private final InventoryServiceClient inventoryService;

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        log.debug("getOnHandInventory: {}", beerId);
        return inventoryService.getOnhandInventory(beerId).stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
    }
}

@FeignClient(name = "inventory-service")
interface InventoryServiceClient {
    String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

    @RequestMapping(method = RequestMethod.GET, value = INVENTORY_PATH)
    List<BeerInventoryDto> getOnhandInventory(@PathVariable UUID beerId);
}
