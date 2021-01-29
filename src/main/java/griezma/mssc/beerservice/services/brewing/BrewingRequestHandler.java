package griezma.mssc.beerservice.services.brewing;

import griezma.mssc.beerservice.config.JmsConfig;
import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.brewery.model.BeerDto;
import griezma.mssc.brewery.model.events.BeerInventoryEvent;
import griezma.mssc.brewery.model.events.BrewingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingRequestHandler {
    private final TaskScheduler scheduler;
    private final JmsTemplate jms;
    private final BeerRepository repo;

    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    void handleBrewingRequest(BrewingRequest brewingRequest) {
        startBrewing(brewingRequest);
    }

    private void startBrewing(BrewingRequest brewingReq) {
        log.debug("Start brewing: {}, quantity={}", brewingReq, brewingReq.getQuantityToBrew());
        log.debug("Beer exists: " + repo.existsById(brewingReq.getBeer().getId()));
        Random random = new Random();
        int duration = 7 + (int)(7 * Math.random());
        scheduler.schedule(() -> {
            brewingFinished(brewingReq);

        }, Instant.now().plus(Duration.ofSeconds(duration)));
    }

    private void brewingFinished(BrewingRequest brewingReq) {
        log.debug("Brewing finished: {}, quantity={}", brewingReq, brewingReq.getQuantityToBrew());
        BeerDto beer = brewingReq.getBeer();
        log.debug("Beer exists: " + repo.existsById(beer.getId()));
        Beer beerEntity = repo.findById(beer.getId()).orElseThrow(() -> new NoSuchElementException("Beer not found by id: " + beer.getId()));
        beer.setQuantityOnHand(beerEntity.getMinOnHand() + brewingReq.getQuantityToBrew());
        jms.convertAndSend(JmsConfig.INVENTORY_EVENT_QUEUE, BeerInventoryEvent.builder()
                .beer(beer)
                .quantityAdded(brewingReq.getQuantityToBrew())
                .build());
    }
}
