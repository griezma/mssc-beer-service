package griezma.mssc.beerservice.services.inventory;

import griezma.mssc.brewery.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Profile("localdiscovery")
@FeignClient(name = "inventory-service")
interface InventoryServiceFeignClient extends InventoryServiceClient {

    @Override
    @RequestMapping(method = RequestMethod.GET, value = InventoryServiceClient.INVENTORY_PATH)
    List<BeerInventoryDto> getOnhandInventoryList(@PathVariable UUID beerId);
}
