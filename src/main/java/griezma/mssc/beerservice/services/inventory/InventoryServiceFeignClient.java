package griezma.mssc.beerservice.services.inventory;

import griezma.mssc.beerservice.config.FeignClientConfig;
import griezma.mssc.brewery.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Profile("cloudconfig")
@FeignClient(name = "inventory-service", configuration = FeignClientConfig.class)
public interface InventoryServiceFeignClient extends InventoryServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = InventoryServiceClient.INVENTORY_PATH)
    List<BeerInventoryDto> getOnhandInventoryList(@PathVariable UUID beerId);
}