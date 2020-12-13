package griezma.mssc.beerservice.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameContainingIgnoreCase(String beerName, Pageable of);

    Page<Beer> findAllByBeerStyleContainingIgnoreCase(String beerStyle, Pageable of);

    Optional<Beer> findByUpc(String upc);
}
