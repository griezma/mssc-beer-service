package griezma.mssc.brewery.model.events;

import griezma.mssc.brewery.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class BrewingRequest {
    private BeerDto beer;
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private Integer quantityToBrew;
}
