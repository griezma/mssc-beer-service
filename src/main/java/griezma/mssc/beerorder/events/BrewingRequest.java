package griezma.mssc.beerorder.events;

import griezma.mssc.brewery.model.BeerDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BrewingRequest extends BeerEvent {
    @Builder
    public BrewingRequest(BeerDto beer, Integer quantityToBrew) {
        super(beer);
        this.quantityToBrew = quantityToBrew;
    }

    private final Integer quantityToBrew;
}
