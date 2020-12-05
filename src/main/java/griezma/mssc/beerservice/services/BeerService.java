package griezma.mssc.beerservice.services;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.data.BeerRepository;
import griezma.mssc.beerservice.data.mapper.BeerMapper;
import griezma.mssc.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
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
                .map(mapper::asBeerDto);
    }

    public BeerDto saveBeer(BeerDto beerDto) {
        Beer saved = repo.save(mapper.toBeer(beerDto));
        return mapper.asBeerDto(saved);
    }

    public void removeBeerById(UUID beerId) {
        repo.deleteById(beerId);
    }

    public void updateBeer(UUID beerId, BeerDto beerDto) {
        var beer = repo.findById(beerId).get();

        Beer changed = mapper.toBeer(beerDto);
        changed.setId(beerId);
        repo.save(changed);
    }
}
