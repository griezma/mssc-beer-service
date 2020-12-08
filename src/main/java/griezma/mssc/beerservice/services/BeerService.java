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

@RequiredArgsConstructor
@Service
public class BeerService {
    private final BeerRepository repo;
    private final BeerMapper mapper;

    public Optional<BeerDto> findBeerById(UUID beerId) {
        return repo.findById(beerId)
                .map(mapper::beerToDto);
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

    public Page<BeerDto> findBeers(String beerName, String beerStyle, PageRequest of) {
        if (beerName != null) {
            return repo.findAllByBeerNameContainingIgnoreCase(beerName, of).map(mapper::beerToDto);
        } else if (beerStyle != null) {
            return repo.findAllByBeerStyleContainingIgnoreCase(beerStyle, of).map(mapper::beerToDto);
        } else {
            return repo.findAll(of).map(mapper::beerToDto);
        }

    }
}
