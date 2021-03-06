package griezma.mssc.beerservice.services.brewing;

import griezma.mssc.beerservice.api.mapper.BeerMapper;
import griezma.mssc.beerservice.config.JmsConfig;
import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.services.inventory.InventoryServiceClient;
import griezma.mssc.brewery.model.events.BrewingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final JmsTemplate jms;
    private final BeerRepository repo;
    private final InventoryServiceClient inventoryService;
    private final BeerMapper dtoMapper;

    @Scheduled(fixedRate = 5000)
    void checkForLowInventory() {
        log.debug("Checking inventory");
        var allBeers = repo.findAll();
        StreamSupport.stream(allBeers.spliterator(), false)
                .filter(this::brewingRequired)
                .forEach(this::sendBrewingRequest);
    }

    private boolean brewingRequired(Beer beer) {
        Integer onHand = inventoryService.getOnhandInventory(beer.getId());
        boolean brewingRequired = onHand < beer.getMinOnHand();
        if (brewingRequired)
            log.debug("Brewing required: {}, onhand {} < {}", beer.getId(), onHand, beer.getMinOnHand());
        return brewingRequired;
    }

    private void sendBrewingRequest(Beer beer) {
        log.debug("sendBrewingRequest {}", beer.getId());
        log.debug("Beer exists: " + repo.existsById(beer.getId()));
        BrewingRequest brewingRequest = BrewingRequest.builder()
                .beer(dtoMapper.beerToDto(beer))
                .quantityToBrew(beer.getQuantityToBrew())
                .build();

        jms.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, brewingRequest);
    }
}
