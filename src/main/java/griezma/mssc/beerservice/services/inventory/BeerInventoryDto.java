package griezma.mssc.beerservice.services.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor
public class BeerInventoryDto {
    private UUID id;
    private OffsetDateTime created;
    private OffsetDateTime lastModified;
    private UUID beerId;
    private String upc;
    private Integer quantityOnHand;
}
