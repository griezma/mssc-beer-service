package griezma.mssc.beerservice.bootstrap;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.api.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository repo;

    public BeerLoader(BeerRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            Beer saved;
            saved = repo.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE.toString())
                    .price(BigDecimal.valueOf(2.99))
                    .quantityToBrew(10000)
                    .build());
            log.debug("created: " + saved);

            saved = repo.save(Beer.builder()
                    .beerName("Mango Queen")
                    .beerStyle(BeerStyle.IPA.toString())
                    .price(BigDecimal.valueOf(3.99))
                    .quantityToBrew(10000)
                    .build());
            log.debug("created: " + saved);
        }
    }
}
