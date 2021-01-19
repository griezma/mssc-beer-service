package griezma.mssc.beerservice.services.inventory;

import java.util.UUID;

public interface InventoryService {
    Integer getOnhandInventory(UUID beerId);
}
