package griezma.mssc.beerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data @Builder @AllArgsConstructor
public class BeerDto {
    @Null
    private UUID id;
    @NotBlank
    private String beerName;
    @NotBlank
    private BeerStyle beerStyle;
    @Null
    private OffsetDateTime createdDate;
    @Null
    private OffsetDateTime lastModifiedDate;
    @Positive
    private Long upc;
    @Positive @Max(200)
    private BigDecimal price;
    @Positive @Max(2000)
    private Integer quantityOnHand;
}
