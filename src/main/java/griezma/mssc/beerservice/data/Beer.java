package griezma.mssc.beerservice.data;

import lombok.*;
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
    @GeneratedValue
    @Column(columnDefinition = "varbinary(32)", updatable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
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
