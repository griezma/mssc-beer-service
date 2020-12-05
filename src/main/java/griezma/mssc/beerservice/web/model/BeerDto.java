package griezma.mssc.beerservice.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static griezma.mssc.beerservice.web.model.Formats.ISO_DATETIME;

@Data @Builder @AllArgsConstructor
public class BeerDto {

    @Null
    private UUID id;
    @NotBlank
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @Null
    @JsonFormat(pattern = ISO_DATETIME, timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;
    @Null
    @JsonFormat(pattern = ISO_DATETIME, timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime lastModifiedDate;
    private String upc;
    @Positive @Max(200)
    private BigDecimal price;
    @Positive @Max(2000)
    private Integer quantityOnHand;
}
