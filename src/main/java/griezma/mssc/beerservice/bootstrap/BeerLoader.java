package griezma.mssc.beerservice.bootstrap;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.web.model.BeerStyle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository repo;

    public BeerLoader(BeerRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            repo.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE.toString())
                    .price(BigDecimal.valueOf(10.25))
                    .quantityToBrew(10000)
                    .build());

            repo.save(Beer.builder()
                    .beerName("Mango Queen")
                    .beerStyle(BeerStyle.IPA.toString())
                    .price(BigDecimal.valueOf(12))
                    .quantityToBrew(10000)
                    .build());
        }
    }
}
