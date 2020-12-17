package griezma.mssc.beerservice.events;

import griezma.mssc.beerservice.api.model.BeerDto;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class BeerEvent implements Serializable {
    static final long serialVersionUID = -2806840971337102266L;

    private BeerDto beer;
    private final OffsetDateTime timestamp = OffsetDateTime.now();
}
