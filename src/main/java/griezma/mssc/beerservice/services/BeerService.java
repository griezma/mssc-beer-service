package griezma.mssc.beerservice.services;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.api.model.BeerDto;
import griezma.mssc.beerservice.api.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class BeerService {
    private final BeerRepository repo;
    private final BeerMapper mapper;

    @Cacheable(cacheNames = "beerCache", key="#beerId", condition = "#inventory == false")
    public Optional<BeerDto> findBeerById(UUID beerId, boolean inventory) {
        log.debug("caching? findBeerById was called");
        return repo.findById(beerId)
                .map(dtoMapping(inventory));
    }

    public BeerDto saveBeer(BeerDto beerDto) {
        Beer saved = repo.save(mapper.dtoToBeer(beerDto));
        return mapper.beerToDto(saved);
    }

    public void removeBeerById(UUID beerId) {
        repo.deleteById(beerId);
    }

    public void updateBeer(UUID beerId, BeerDto beerDto) {
        var beer = repo.findById(beerId).get();

        Beer changed = mapper.dtoToBeer(beerDto);
        changed.setId(beerId);
        repo.save(changed);
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#inventory == false")
    public Page<BeerDto> listBeers(String beerName, String beerStyle, boolean inventory, PageRequest of) {
        log.debug("caching? listBeers was called");
        if (beerName != null) {
            return repo.findAllByBeerNameContainingIgnoreCase(beerName, of).map(dtoMapping(inventory));
        } else if (beerStyle != null) {
            return repo.findAllByBeerStyleContainingIgnoreCase(beerStyle, of).map(dtoMapping(inventory));
        } else {
            return repo.findAll(of).map(dtoMapping(inventory));
        }

    }

    private Function<Beer, BeerDto> dtoMapping(boolean includeInventory) {
        return includeInventory ? mapper::beerToDtoWithInventory : mapper::beerToDto;
    }
}
