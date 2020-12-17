package griezma.mssc.beerservice.services.brewing;

import griezma.mssc.beerservice.api.model.BeerDto;
import griezma.mssc.beerservice.config.JmsConfig;
import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.events.BeerInventoryEvent;
import griezma.mssc.beerservice.events.BrewingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
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
        Random random = new Random();
        int duration = 7 + (int)(7 * Math.random());
        scheduler.schedule(() -> {
            brewingFinished(brewingReq);

        }, Instant.now().plus(Duration.ofSeconds(duration)));
    }

    private void brewingFinished(BrewingRequest brewingReq) {
        log.debug("brewingFinished: {}", brewingReq);
        BeerDto beer = brewingReq.getBeer();
        Beer beerEntity = repo.findById(beer.getId()).orElseThrow();
        beer.setQuantityOnHand(beerEntity.getMinOnHand() + brewingReq.getQuantityToBrew());
        jms.convertAndSend(JmsConfig.INVENTORY_EVENT_QUEUE, BeerInventoryEvent.builder()
                .beer(beer)
                .quantityAdded(brewingReq.getQuantityToBrew())
                .build());
    }
}
