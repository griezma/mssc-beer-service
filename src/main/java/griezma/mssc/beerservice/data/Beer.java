package griezma.mssc.beerservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Beer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDBinaryType")
    @Column(columnDefinition = "binary(16)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastModified;

    @Column(nullable = false)
    private String beerName;

    @Column(nullable = false)
    private String beerStyle;

    @Column(unique = true)
    private String upc;

    private BigDecimal price;

    private Integer quantityToBrew;
}
