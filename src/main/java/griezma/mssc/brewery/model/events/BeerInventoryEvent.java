package griezma.mssc.brewery.model.events;

import griezma.mssc.brewery.model.BeerDto;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class BeerInventoryEvent {
    static final long serialVersionUID = -2806840971337102266L;

    private BeerDto beer;
    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private Integer quantityAdded;
}
