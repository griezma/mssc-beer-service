package griezma.mssc.beerservice.services.inventory;

import griezma.mssc.brewery.model.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

public interface InventoryServiceClient {
    String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

    List<BeerInventoryDto> getOnhandInventoryList(UUID beerId);

    default Integer getOnhandInventory(UUID beerId) {
        return getOnhandInventoryList(beerId).stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
    }
}
