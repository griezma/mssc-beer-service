package griezma.mssc.beerservice.services;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.api.model.BeerDto;
import griezma.mssc.beerservice.api.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BeerService {
    private final BeerRepository repo;
    private final BeerMapper mapper;

    public Optional<BeerDto> findBeerById(UUID beerId, boolean includeInventory) {
        return repo.findById(beerId)
                .map(dtoMapping(includeInventory));
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

    public Page<BeerDto> findBeers(String beerName, String beerStyle, boolean inventory, PageRequest of) {
        if (beerName != null) {
            return repo.findAllByBeerNameContainingIgnoreCase(beerName, of).map(dtoMapping(inventory));
        } else if (beerStyle != null) {
            return repo.findAllByBeerStyleContainingIgnoreCase(beerStyle, of).map(dtoMapping(inventory));
        } else {
            return repo.findAll(of).map(mapper::beerToDto);
        }

    }

    private Function<Beer, BeerDto> dtoMapping(Boolean includeInventory) {
        return includeInventory ? mapper::beerToDtoWithInventory : mapper::beerToDto;
    }
}
