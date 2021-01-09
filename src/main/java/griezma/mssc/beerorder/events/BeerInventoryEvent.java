package griezma.mssc.beerorder.events;

import griezma.mssc.brewery.model.BeerDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BeerInventoryEvent extends BeerEvent {

    private final Integer quantityAdded;

    @Builder
    public BeerInventoryEvent(BeerDto beer, Integer quantityAdded) {
        super(beer);
        this.quantityAdded = quantityAdded;
    }
}
