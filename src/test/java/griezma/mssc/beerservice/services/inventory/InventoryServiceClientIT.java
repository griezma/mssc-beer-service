package griezma.mssc.beerservice.services.inventory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
public class InventoryServiceClientIT {
    @Autowired
    InventoryService client;

    private final UUID existingBeerId = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");

    @Test @Timeout(100)
    void worksForExistingBeer() {
        Integer result = client.getOnhandInventory(existingBeerId);
        assertThat(result).isGreaterThan(0);
    }

    @Test @Timeout(100)
    void resolvesToZeroForUnknownBeer() {
        Integer result = client.getOnhandInventory(UUID.randomUUID());
        assertThat(result).isZero();
    }
}
