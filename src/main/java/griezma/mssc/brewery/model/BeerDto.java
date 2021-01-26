package griezma.mssc.brewery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data @Builder @AllArgsConstructor
@ToString
public class BeerDto {
//    static final long serialVersionUID = 7220034028643401220L;

    @Null
    private UUID id;

    @NotBlank
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @Null
    @JsonFormat(timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime created;

    @Null
    @JsonFormat(timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime lastModified;

    private String upc;

    @Positive @Max(200)
    private BigDecimal price;

    @Positive
    private Integer quantityOnHand;
}
